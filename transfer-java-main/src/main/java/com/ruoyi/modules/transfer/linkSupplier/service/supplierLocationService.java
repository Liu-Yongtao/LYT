package com.ruoyi.modules.transfer.linkSupplier.service;

import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocation;

import java.util.ArrayList;
import java.util.HashMap;

public interface supplierLocationService {

    /**
     * 条件获取渠道信息列表
     * */
    HashMap<String,Object> getSupplierLocationList(HashMap<String,Object> map);

    /**
     * 新增
     * */
    int insertSupplierLocation(supplierLocation supplierLocation);

    /**
     * 修改
     * */
    int updateSupplierLocation(supplierLocation supplierLocation);

    /**
     * 获取通道的id和名称
     * */
    ArrayList<supplierLocation> getPassageIdAndName();


}
