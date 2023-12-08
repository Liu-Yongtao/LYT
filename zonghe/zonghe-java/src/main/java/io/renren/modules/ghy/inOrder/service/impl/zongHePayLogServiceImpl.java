package io.renren.modules.ghy.inOrder.service.impl;

import com.alibaba.fastjson.JSONObject;

import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.goodsSupplier.service.impl.cashierUserServiceImpl;
import io.renren.modules.ghy.goodsSupplier.service.impl.goodsListServiceImpl;
import io.renren.modules.ghy.inOrder.dao.zongHePayLogMapper;
import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;
import io.renren.modules.ghy.inOrder.service.send.a_sendRoute;
import io.renren.modules.ghy.inOrder.service.zongHePayLogService;
import io.renren.modules.ghy.supplier.service.impl.locationServiceImpl;
import io.renren.modules.ghy.util.CommomUtil;
import io.renren.modules.ghy.util.OutTradeNoGenerator;
import io.renren.modules.ghy.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.Map;

@Service
public class zongHePayLogServiceImpl implements zongHePayLogService {

    @Autowired
    private zongHePayLogMapper zongHePayLogMapper;

    @Autowired
    private cashierUserServiceImpl cashierUserService;

    @Autowired
    private goodsListServiceImpl goodsListService;

    @Autowired
    private locationServiceImpl locationService;

    //策略集合
    @Autowired
    private Map<String, a_sendRoute> sendRouteMap;

    private Logger logger = LoggerFactory.getLogger(zongHePayLogServiceImpl.class);

    private String beforeTable = "w_cashier_pay_log_";

    /**
     * 进单，入库Service层
     * */
    @Override
    public void insertZongHe(HashMap<String,String> hashMap, JSONObject map) {
        long time = System.currentTimeMillis();

        //判断是否存在该产品编号
        goods goods = goodsListService.isBySn(map.getString("productsn"));
        if(null == goods){
            throw new RuntimeException("不存在该产品编号或已下架");
        }

        //与产品信息的金额比对,金额有比如 5.00、5、5.0
        // 所以统一使用BigDecimal的compareTo比较，=0相等；<0 小于；>0 大于
        BigDecimal money = new BigDecimal(map.getString("money"));
        if(new BigDecimal(String.valueOf(goods.getPrice())).compareTo(money) != 0){
            throw new RuntimeException("金额错误");
        }

        //校验身份，client_id、client_secret
        //顺便获取商户表中需要的信息，id
        HashMap<String, Object> cashierUser = cashierUserService.signCashierUser(hashMap.get("client_id"), hashMap.get("client_secret"));
        if(null == cashierUser || null == cashierUser.get("id")){
            throw new RuntimeException("身份权限校验错误");
        }

        // 校验sign加密
        String sign = "";
        try {
            sign = SignUtil.createSign(map.getInnerMap(),hashMap.get("client_id"),hashMap.get("client_secret"),false);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("未知错误");
        }

        if(!sign.equals(hashMap.get("sign"))){
            throw new RuntimeException("校验失败，请检查sign是否正确");
        }

        //获取策略信息
        HashMap<String, Integer> location = locationService.locationBySnCashierId(map.getString("productsn"), cashierUser.get("id").toString());

        //写入
        zonghePayLog zonghePayLog = new zonghePayLog();
        zonghePayLog.setOutLogno(map.getString("out_logno"));
        zonghePayLog.setAccount(map.getString("account"));
        zonghePayLog.setMoney(money);
        zonghePayLog.setProductsn(map.getString("productsn"));
        zonghePayLog.setCashierid((Integer) cashierUser.get("id"));
        zonghePayLog.setGoodsid(goods.getId());
        zonghePayLog.setCategory(goods.getCategoryid());
        zonghePayLog.setFromid(goods.getFromid());
        zonghePayLog.setLocation(location.get("id"));

        //平台流水ID
        String longo = OutTradeNoGenerator.generateOutTradeNo("J",time);
        zonghePayLog.setLogno(longo);
        hashMap.put("logno",longo);
        //创建时间
        zonghePayLog.setCreatetime(CommomUtil.getTimeorTableName("","yyyy-MM-dd HH:mm:ss",time,""));
        zonghePayLog.setTablename(CommomUtil.getTimeorTableName(zonghePayLog.getTablename()+"_","YYYY",time,""));
        try {
            zongHePayLogMapper.insertZongHe(zonghePayLog);
        } catch (DuplicateKeyException e) {
            logger.error("插入数据失败,商户订单号重复=>"+hashMap);
            throw new RuntimeException("商户订单号重复");
        } catch (Exception e){
            logger.error("插入数据失败=>"+e.getMessage());
            throw new RuntimeException("未知错误");
        }
        //异步执行渠道策略
        a_sendRoute send = sendRouteMap.get(location.get("supplierid1").toString());
        send.handler();
    }

    @Override
    public HashMap<String, Object> getCountZongHe(String yearnumber) {
        return zongHePayLogMapper.getCountZongHe(beforeTable+yearnumber);
    }

    @Override
    public int delZongHeById(String id,String tableName) {
        return zongHePayLogMapper.delZongHeById(id,tableName);
    }

    @Override
    public int delAllZongHePayLog(String yearnumber) {
        int i = zongHePayLogMapper.delAllZongHePayLog(beforeTable+yearnumber);
        //删除表的所有数据，并自增置为一
        zongHePayLogMapper.alterAutoInt(beforeTable+yearnumber);
        return i;
    }

    @Override
    public HashMap<String, Object> getSelectZongHelimit(String account,String logno,String money,Integer status,int stat, int end,String yearNumber) {
        if(account.equals("")){
            account = null;
        }
        if(logno.equals("")){
            logno=null;
        }
        if(money.equals("")){
            money = null;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sumCount",zongHePayLogMapper.getSelectZongHelimit(account,logno,money,status,beforeTable+yearNumber));
        hashMap.put("userList",zongHePayLogMapper.getAllZongHelimit(account,logno,money,status,stat,end,beforeTable+yearNumber));
        return hashMap;
    }

    @Override
    public void updateZongHe(zonghePayLog zonghePayLog) {
        zongHePayLogMapper.updateZongHe(zonghePayLog);
    }


    @Override
    public zonghePayLog getZonghePayLogById(int id,String yearNnumber) {
        return zongHePayLogMapper.getZonghePayLogById(id,beforeTable+yearNnumber);
    }


}
