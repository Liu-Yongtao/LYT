package com.ruoyi.modules.transfer.linkSupplier.mapper;

import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface supplierLocationMapper {

    /**
     * 条件获取通道
     * */
    ArrayList<supplierLocation> getSupplierLocationList(HashMap<String,Object> map);

    /**
     * 统计满足条件通道
     * */
    int sumCount(HashMap<String,Object> map);

    /**
     * 新增通道
     * */
    int insertSupplierLocation(supplierLocation supplierLocation);

    /**
     * 修改通道
     * */
    int updateSupplierLocation(supplierLocation supplierLocation);

    /**
     * 根据id查询通道
     * */
    supplierLocation getSupplierLocationById(Integer id);

    /**
     * 获取通道的id和名称
     * */
    ArrayList<supplierLocation> getPassageIdAndName();



}
