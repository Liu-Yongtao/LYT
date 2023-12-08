package io.renren.modules.ghy.goodsSupplier.dao;

import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface goodsListMapper {

    /**
     * 获取产品列表
     * goodsList 为产品列表
     * sumCount  为字段数量
     * */
    List<goods> goodsList(HashMap<String,Object> hashMap);

    /**
     * 获取总数量
     * */
    int goodsCount(HashMap<String,Object> hashMap);


    /**
     * 添加产品
     * */
    int insterGoods(goods goods);
    /**
     * 条件更新产品
     * */
    int updateGoodsList(goods goods);

    /**
     * 通过产品编号获取金额，判断是否存在该产品
     * */
    goods isBySn(String sn);
}
