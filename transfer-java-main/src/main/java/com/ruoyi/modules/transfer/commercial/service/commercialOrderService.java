package com.ruoyi.modules.transfer.commercial.service;


import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;

import java.util.HashMap;
import java.util.LinkedHashMap;

public interface commercialOrderService {

    /**
     * 分页条件获取商户订单列表
     * @return  HashMap
     * */
    HashMap<String,Object> getCommercialOrderList(HashMap<String,Object> map);

    /**
     * 商户进单
     * @return payUrl 支付链接
     * */
    String inCommercialOrder(LinkedHashMap<String,Object> map);



    /**
     * 商户订单成功并通知回调
     * */
    void commercialNotify(commercialOrder commercialOrder);

}
