package io.renren.modules.ghy.goodsSupplier.service;

import io.renren.modules.ghy.goodsSupplier.pojo.goods;

import java.util.HashMap;
import java.util.List;

public interface goodsListService {

    /**
     * 获取产品列表
     * goodsList 为产品列表
     * sumCount  为字段数量
     * */
    HashMap<String,Object> goodsList(HashMap<String,Object> hashMap);

    /**
     * 添加产品
     * */
    String insterGoods(goods goods);

    /**
     * 条件更新产品
     * */
    String updateGoodsList(goods goods);

    /**
     * 通过产品编号获取金额，判断是否存在该产品
     * */
    goods isBySn(String sn);
}
