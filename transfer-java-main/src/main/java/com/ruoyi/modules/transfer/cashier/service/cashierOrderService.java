package com.ruoyi.modules.transfer.cashier.service;



import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;


public interface cashierOrderService{

    /**
     * 接收话商传来的参数
     * */
    void inCashierOrder(LinkedHashMap<String,Object> map);

    /**
     * 分页条件获取商户订单列表
     * @return  HashMap
     * */
    HashMap<String,Object> getCashierOrderList(HashMap<String,Object> map);


    /**
     * 商户匹配话商订单
     * @param money 金额，type 渠道类型
     * */
    cashierOrder commercialNormalOrder(BigDecimal money, Integer type);

    /**
     * 订单失败并通知
     * */
    void loseOrderNotify(cashierOrder cashierOrder,boolean timeOut);

    /**
     * 订单成功并通知
     * */
    void successOrderNotify(cashierOrder cashierOrder);


    /**
     * 通过平台订单号获取话商订单
     * */
    cashierOrder getCashierOrderByLogno(String logno);

}
