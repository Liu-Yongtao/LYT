package io.renren.modules.ghy.goodsSupplier.service;

import io.renren.modules.ghy.goodsSupplier.pojo.cashierCategory;
import io.renren.modules.ghy.goodsSupplier.pojo.goodsCategory;

import java.util.HashMap;
import java.util.List;

public interface categoryService {

    /**
     * 获取产品类型列表
     * */
    List<HashMap<String,Object>> categoryIdAndName();

    /**
     * 获取产品类型列表
     * */
   HashMap<String,Object> cashierCategoryList(String name, Integer status,int staLimit, int endLimit);

    /**
     * 新增产品类型
     * */
    String insertCashierCategory(cashierCategory cashierCategory);

    /**
     * 条件更新产品类型表
     * */
    String updateCashierCategory(cashierCategory cashierCategory);


//================================ 业务类型 ================================

    /**
     * 获取产品类型列表
     * */
    List<HashMap<String,Object>> goodsCategoryIdAndName();

    /**
     * 获取产品类型列表
     * */
    HashMap<String,Object> goodsCategoryList(String name, Integer status,int staLimit, int endLimit);

    /**
     * 新增产品类型
     * */
    String insertgoodsCategory(goodsCategory goodsCategory);

    /**
     * 条件更新产品类型表
     * */
    String updategoodsCategory(goodsCategory goodsCategory);


}
