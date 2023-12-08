package io.renren.modules.ghy.supplier.service;

import io.renren.modules.ghy.supplier.pojo.location;

import java.util.HashMap;
import java.util.List;

public interface locationService {

    /**
     * 获取供货商策略列表
     * supplierList 为供货商策略列表
     * sumCount 总数量
     * */
    HashMap<String,Object> getSupplierLocationList(String name,Integer status, int staLimit, int endLimit);

    /**
     * 新增供货商
     * */
    String insertSupplierLocation(location location);

    /**
     * 条件修改
     * */
    String updateSupplierLocation(location location);

    /**
     * 根据产品编号 和 商户id获取策略信息
     * */
    HashMap<String,Integer> locationBySnCashierId(String sn,String cashierid);

}
