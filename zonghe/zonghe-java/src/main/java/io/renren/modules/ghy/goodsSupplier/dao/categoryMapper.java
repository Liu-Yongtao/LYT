package io.renren.modules.ghy.goodsSupplier.dao;

import io.renren.modules.ghy.goodsSupplier.pojo.cashierCategory;
import io.renren.modules.ghy.goodsSupplier.pojo.goodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface categoryMapper {

    /**
     * 获取产品类型列表
     * */
    List<HashMap<String,Object>> categoryIdAndName();

    /**
     * 获取产品类型列表
     * */
    List<cashierCategory> cashierCategoryList(String name,Integer status,int staLimit,int endLimit);

    /**
     * 获取总行数
     * */
    int sumCount(String name,Integer status,String tableName);

    /**
     * 新增产品类型
     * */
    int insertCashierCategory(cashierCategory cashierCategory);

    /**
     * 条件更新产品类型表
     * */
    int updateCashierCategory(cashierCategory cashierCategory);

//==================================== 业务类型 ===============================

    /**
     * 获取业务类型列表
     * */
    List<HashMap<String,Object>> goodsCategoryIdAndName();

    /**
     * 获取业务类型列表
     * */
    List<cashierCategory> goodsCategoryList(String name,Integer status,int staLimit,int endLimit);

    /**
     * 新增业务类型
     * */
    int insertGoodsCategory(goodsCategory goodsCategory);

    /**
     * 条件更新业务类型表
     * */
    int updateGoodsCategory(goodsCategory goodsCategory);

}
