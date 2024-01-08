package com.ruoyi.modules.transfer.cashier.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.modules.transfer.cashier.mapper.cashierOrderMapper;
import com.ruoyi.modules.transfer.cashier.mapper.cashierUserMapper;
import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import com.ruoyi.modules.transfer.cashier.pojo.cashierUser;
import com.ruoyi.modules.transfer.cashier.service.cashierOrderService;
import com.ruoyi.modules.utils.OutTradeNoGenerator;
import com.ruoyi.modules.utils.SendHttp;
import com.ruoyi.modules.utils.SignUtil;
import com.ruoyi.modules.utils.phoneAreaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class cashierOrderServiceImpl implements cashierOrderService {

    @Autowired
    private cashierOrderMapper cashierOrderMapper;

    @Autowired
    private cashierUserMapper cashierUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void inCashierOrder(LinkedHashMap<String, Object> map){
        String apiKey = (String) map.get("apiKey");
        cashierUser cashierUser = cashierUserMapper.getCashierUserByApiKey(apiKey);
        if(null == cashierUser){
            throw new RuntimeException("暂无权限");
        }

        String sign = (String) map.get("sign");
        map.remove("sign");
        map.remove("apiKey");
        try {
            String sign1 = SignUtil.createSign(map, "", "&"+apiKey, false);
            if(!sign1.equals(sign)){
                throw new RuntimeException("校验失败");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("校验失败");
        }
        // 获取号码的归属地和运营商
        HashMap<String, Object> areaAndType = phoneAreaUtil.getPhoneAreaAndType((String) map.get("account"));
        //数据映射
        cashierOrder cashierOrder = new cashierOrder();
        cashierOrder.setCashierId(cashierUser.getId());
        cashierOrder.setAccount((String)map.get("account"));
        cashierOrder.setOutLogno((String) map.get("out_logno"));
        cashierOrder.setMoney(new BigDecimal(map.get("money")+""));
        cashierOrder.setNotifyUrl(cashierUser.getNotifyUrl());
        cashierOrder.setType((Integer) map.get("type"));
        cashierOrder.setArea((String) areaAndType.get("area"));
        cashierOrder.setTelephoneType((Integer) areaAndType.get("type"));
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cashierOrder.setLogno(OutTradeNoGenerator.generateOutTradeNo("P",time));
        cashierOrder.setStatTime(format.format(time));
        try {
            cashierOrderMapper.inCashierOrder(cashierOrder);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("进单失败，订单号重复");
        }
        long timeout = cashierOrder.getType()==0?600:(60*60*24);
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set("order:"+cashierOrder.getLogno(),cashierOrder.getLogno(),timeout, TimeUnit.SECONDS);


    }

    /**
     * 分页条件获取商户订单列表
     * @return  HashMap
     *          --cashierOrderList 商户订单列表
     *          --sun    统计
     *              -userCount
     *              -moneyCount
     * */
    @Override
    public HashMap<String,Object> getCashierOrderList(HashMap<String, Object> map) {

        map.forEach((key, value) -> {
            if (value.equals("")) {
                map.put(key, null);
            }
        });

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("cashierOrderList",cashierOrderMapper.getCashierOrderList(map));
        hashmap.put("sum",cashierOrderMapper.sumCashierOrder(map));
        return hashmap;
    }

    /**
     *  通过平台订单号获取话商订单
     * @param logno 平台订单号
     */
    @Override
    public cashierOrder getCashierOrderByLogno(String logno) {
        return cashierOrderMapper.getCashierOrderByLogno(logno);
    }


    /**
     *  匹配订单
     * @param money 金额
     * @param type 渠道类型
     * @return cashierOrder 匹配到的订单对象，为 null 代表未匹配到
     * */
    @Override
    public cashierOrder commercialNormalOrder(BigDecimal money,Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("money",money);
        map.put("type",type);
        map.put("normalStatus",0);
        map.put("stat",0);
        map.put("end",1);
        List<cashierOrder> cashierOrderList = null;
        //同步代码块，避免商户多笔订单匹配到同一笔订单
        synchronized (cashierOrderMapper){
            //当渠道类型为混合时，优先找快充
            if(type == 2){
                map.put("type",0);
                cashierOrderList = cashierOrderMapper.getCashierOrderList(map);
                if(cashierOrderList.size() == 0){
                    map.put("type",1);
                    cashierOrderList = cashierOrderMapper.getCashierOrderList(map);
                }
            }else{
                cashierOrderList = cashierOrderMapper.getCashierOrderList(map);
            }
            //读取到订单需要将订单置为匹配成功
            if(cashierOrderList.size()>0){
                // 新建一个对象写入数据，这样可以减少写入字段重复写入
                cashierOrder cashierOrder = new cashierOrder();
                cashierOrder.setOutLogno(cashierOrderList.get(0).getOutLogno());
                cashierOrder.setNormalStatus(2);
                cashierOrderMapper.updateCashierOrder(cashierOrder);
                return cashierOrderList.get(0);
            }
        }
        return null;
    }

    /**
     * 异步，订单失败并回调
     * 失败有几种情况
     * 1、订单超时：不管快充慢充都要返回并失败
     * 2、订单失败：对于快充都回调，对于慢充则还可以进行下次匹配
     *
     * @param cashierOrder 话商订单对象，需要先将状态修改
     * @param timeOut 是否时间超限的订单
     * */
    @Override
    @Async
    public void loseOrderNotify(cashierOrder cashierOrder,boolean timeOut) {
        //是慢充但不是过期的情况
        if(!timeOut && cashierOrder.getType() == 1){
            cashierOrder.setNormalStatus(0);
            cashierOrder.setStatus(0);
            //写入数据
            cashierOrderMapper.updateCashierOrder(cashierOrder);
            return;
        }
        cashierOrder.setEndTime(DateUtils.getTime());
        // 需要发送通知的字段
        HashMap<String, Object> map = new HashMap<>();
        map.put("logno",cashierOrder.getOutLogno());
        map.put("status",1);
        map.put("money",cashierOrder.getMoney());
        cashierOrder.setStatus(1);
       //redis里是删除key
       redisTemplate.delete("order:"+cashierOrder.getLogno());
       try {
            String json = SendHttp.sendCashierPost(map, cashierOrder.getNotifyUrl(), "json");
            JSONObject jsonObject = JSONObject.parseObject(json);
            cashierOrder.setNotifyStatus(jsonObject.getInteger("code")==200?2:1);
            cashierOrderMapper.updateCashierOrder(cashierOrder);
       } catch (IOException e) {
            throw new RuntimeException("异常");
       }
    }


    /**
     * 异步，话商订单成功并回调
     * */
    @Override
    @Async
    public void successOrderNotify(cashierOrder cashierOrder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("logno",cashierOrder.getOutLogno());
        map.put("status",2);
        map.put("money",cashierOrder.getMoney());
        //redis里是删除key
        redisTemplate.delete("order:"+cashierOrder.getLogno());
        try {
            String json = SendHttp.sendCashierPost(map, cashierOrder.getNotifyUrl(), "json");
            JSONObject jsonObject = JSONObject.parseObject(json);
            cashierOrder.setNotifyStatus(jsonObject.getInteger("code")==200?2:1);
            cashierOrderMapper.updateCashierOrder(cashierOrder);
        } catch (IOException e) {
            throw new RuntimeException("异常");
        }
    }


}
