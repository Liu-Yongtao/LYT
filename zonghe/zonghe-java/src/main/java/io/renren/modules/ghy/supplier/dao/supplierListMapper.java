package io.renren.modules.ghy.supplier.dao;

import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.supplier.pojo.supplier;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface supplierListMapper {

    /**
     * 获取供货商列表
     * supplierList 为供货商列表
     * sumCount  为数据总数量
     * */
    List<goods> supplierList(String name,Integer status,int staLimit, int endLimit);

    /**
     * 获取总数量
     * */
    int supplierCount(String name,Integer status);

    /**
     * 添加
     * */
    int insertSupplier(supplier supplier);

    /**
     * 条件修改
     * */

    int updateSupplier(supplier supplier);

    /**
     * 获取所有供货商id和名字
     * */
    List<HashMap<String,Object>> getSupplierIdAndName();

    /**
     * 根据ID获取供货商地址信息
     * */
    String getSupplierUrlById(int id);

}
