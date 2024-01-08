package com.ruoyi.modules.transfer.commercial.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import com.ruoyi.modules.transfer.cashier.service.impl.cashierOrderServiceImpl;
import com.ruoyi.modules.transfer.commercial.mapper.commercialOrderMapper;
import com.ruoyi.modules.transfer.commercial.mapper.commercialUserMapper;
import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;

import com.ruoyi.modules.transfer.commercial.pojo.commercialUser;
import com.ruoyi.modules.transfer.commercial.service.commercialOrderService;

import com.ruoyi.modules.transfer.linkSupplier.mapper.supplierLocationMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocation;
import com.ruoyi.modules.transfer.linkSupplier.service.impl.linkSupplierOrderServiceImpl;
import com.ruoyi.modules.utils.OutTradeNoGenerator;
import com.ruoyi.modules.utils.SendHttp;
import com.ruoyi.modules.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.LinkedHashMap;


@Service
public class commercialOrderServiceImpl implements commercialOrderService {

    @Autowired
    private commercialOrderMapper commercialOrderMapper;

    @Autowired
    private commercialUserMapper commercialUserMapper;

    @Autowired
    private supplierLocationMapper supplierLocationMapper;

    @Autowired
    private linkSupplierOrderServiceImpl linkSupplierOrderService;

    @Autowired
    private cashierOrderServiceImpl cashierOrderService;

    /**
     * 条件获取商户列表
     * */
    @Override
    public HashMap<String, Object> getCommercialOrderList(HashMap<String, Object> map) {
        map.forEach((key, value) -> {
            if (value.equals("")) {
                map.put(key, null);
            }
        });

        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("commercialOrderList",commercialOrderMapper.getCommercialOrderList(map));
        hashmap.put("sum",commercialOrderMapper.sumCommercialOrder(map));
        return hashmap;
    }

    /**
     * 商户进单
     * */
    @Override
    public String inCommercialOrder(LinkedHashMap<String,Object> map) {
        String apiKey = (String) map.get("apiKey");
        //根据apiKey确定商户，并获取 所需信息
        commercialUser commercialUserByApiKey = commercialUserMapper.getCommercialUserByApiKey(apiKey);
        if( null == commercialUserByApiKey){
            throw new RuntimeException("您暂无权限");
        }
        String sign = (String)map.get("sign");
        //删除不需要参与校验的参数
        map.remove("apiKey");
        map.remove("sign");
        String s = "";
        try {
             s = SignUtil.createSign(map,"","&"+apiKey,false);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("校验异常");
        }
        if(!s.equals(sign)){
            throw new RuntimeException("校验失败，请检查");
        }
        // 数据映射
        commercialOrder order = new commercialOrder();
        order.setCommercialId(commercialUserByApiKey.getId());
        order.setCommercialNo(OutTradeNoGenerator.generateOutTradeNo("C"));
        order.setIpaddr((String) map.get("ipaddr"));
        order.setPassage(commercialUserByApiKey.getPassage());
        order.setMoney(new BigDecimal(map.get("money")+""));
        //通过通道ID获取通道
        supplierLocation supplierLocationById = supplierLocationMapper.getSupplierLocationById(commercialUserByApiKey.getPassage());
        order.setZfType((Integer) map.get("zfType"));
        order.setType(supplierLocationById.getType());
        order.setPhoneType((Integer) map.get("phoneType"));
        order.setStatTime(DateUtils.getTime());
        order.setNotifyUrl(commercialUserByApiKey.getNotifyUrl());
        order.setUrlStatus(0);
        order.setNormalStatus(0);
        //匹配订单
        cashierOrder cashierOrder = cashierOrderService.commercialNormalOrder(order.getMoney(), supplierLocationById.getType());
        // 产码 map,产码成功时
        HashMap<String,Object> urlMap = new HashMap<>();
        if(cashierOrder != null){
            order.setNormalStatus(2);
            order.setAccount(cashierOrder.getAccount());
            order.setCashierId(cashierOrder.getCashierId());
            order.setLogno(cashierOrder.getLogno());
            order.setArea(cashierOrder.getArea());
            order.setTelephoneType(cashierOrder.getTelephoneType());
            //产码
            urlMap = linkSupplierOrderService.inLinkSupplierOrder(supplierLocationById.getLocationList(),order);
            //产码失败
            if(urlMap.get("payUrl") == null){
                //产码失败，但话商是匹配成功的
                cashierOrder.setNormalStatus(2);
                cashierOrder.setRemake("订单失败");
                order.setUrlStatus(1);
                order.setEndTime(DateUtils.getTime());
                //产码失败也需要将话商订单返回
                cashierOrderService.loseOrderNotify(cashierOrder,false);
            }
        }else{
            order.setNode("[]");
            order.setNormalStatus(1);
            order.setEndTime(DateUtils.getTime());
        }
        //写进数据库
        try {
            commercialOrderMapper.insertCommercialOrder(order);
        } catch (Exception e) {
            throw new RuntimeException("未知错误");
        }

        return urlMap.get("payUrl") == null? "" : (String) urlMap.get("payUrl");
    }

    /**
     * 异步，商户订单（成功/失败）并回调
     *
     * @param commercialOrder 商户订单信息
     * */
    @Override
    @Async
    public void commercialNotify(commercialOrder commercialOrder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("commercialNO",commercialOrder.getCommercialNo());
        map.put("status",commercialOrder.getPayStatus());
        map.put("money",commercialOrder.getMoney());

        try {
            String json = SendHttp.sendCashierPost(map, commercialOrder.getNotifyUrl(), "json");
            JSONObject jsonObject = JSONObject.parseObject(json);
            commercialOrder.setNotifyStatus(jsonObject.getInteger("code")==200?2:1);
            commercialOrderMapper.updateCommercialOrder(commercialOrder);
        } catch (IOException e) {
            throw new RuntimeException("异常");
        }
    }


}
