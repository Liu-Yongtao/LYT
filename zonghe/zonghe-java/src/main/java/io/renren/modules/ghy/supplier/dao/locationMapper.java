package io.renren.modules.ghy.supplier.dao;

import io.renren.modules.ghy.supplier.pojo.location;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface locationMapper {

    /**
     * 获取供货商策略列表
     * supplierList 为供货商策略列表
     * */
    List<location> getSupplierLocationList(String name,Integer status,int staLimit,int endLimit);

    /**
     * 获取总数量
     * */
    int supplierLocationCount(String name,Integer status);

    /**
     * 新增供货商
     * */
    int insertSupplierLocation(location location);

    /**
     * 条件修改
     * */
    int updateSupplierLocation(location location);


    /**
     * 根据产品编号 和 商户id获取策略信息
     * */
    HashMap<String,Integer> locationBySnCashierId(String sn,String cashierid);

}
