package io.renren.modules.ghy.goodsSupplier.service.impl;

import io.renren.modules.ghy.goodsSupplier.dao.goodsListMapper;
import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.goodsSupplier.service.goodsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class goodsListServiceImpl implements goodsListService {

    @Autowired
    private goodsListMapper goodsListMapper;

    /**
     * 获取产品列表
     * @return goodsList 为产品列表;sumCount为读取行数
     * */
    @Override
    public HashMap<String,Object> goodsList(HashMap<String,Object> hashMap) {

        hashMap.forEach((k,v)->{
            if (v.equals("")){
                hashMap.put(k,null);
            }
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsList",goodsListMapper.goodsList(hashMap));
        map.put("sumCount",goodsListMapper.goodsCount(hashMap));
        return map;
    }

    @Override
    public String insterGoods(goods goods) {
        int i;
        try {
            i = goodsListMapper.insterGoods(goods);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public String updateGoodsList(goods goods) {
        int i;
        try {
            i = goodsListMapper.updateGoodsList(goods);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public goods isBySn(String sn) {
        return goodsListMapper.isBySn(sn);
    }
}
