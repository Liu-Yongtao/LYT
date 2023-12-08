package io.renren.modules.ghy.supplier.service;

import io.renren.modules.ghy.supplier.pojo.supplier;

import java.util.HashMap;
import java.util.List;

public interface supplierListService {

    /**
     * 获取产品列表
     * goodsList 为产品列表
     * sumCount  为字段数量
     * */
    HashMap<String,Object> supplierList(String name,Integer status,int staLimit, int endLimit);

    /**
     * 添加
     * */
    String insertSupplier(supplier supplier);

    /**
     * 条件修改
     * */

    String updateSupplier(supplier supplier);

    /**
     * 获取所有商户id和名字
     * */
    List<HashMap<String,Object>> getSupplierIdAndName();
}
