package com.ruoyi.modules.transfer.linkSupplier.service;

import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;

import java.util.HashMap;

public interface linkSupplierOrderService {
    /**
     * 分页条件获取渠道订单列表
     * @return  HashMap
     * */
    HashMap<String,Object> getLinkSupplierOrderList(HashMap<String,Object> map);

    /**
     * 供应商产图
     * @param locationList 渠道列表
     * */
    HashMap<String,Object> inLinkSupplierOrder(String locationList, commercialOrder commercialOrder);

}
