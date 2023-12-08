package io.renren.modules.ghy.goodsSupplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.goodsSupplier.service.impl.goodsListServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class goodsController {


    @Autowired
    private goodsListServiceImpl goodsListService;
    private Logger log = LoggerFactory.getLogger(goodsController.class);

    /**
     * 获取产品列表
     * */
    @PostMapping("/goods/goodsList")
    public String goodsList(@RequestBody HashMap<String,Object> hashMap){
        String s = "";
        try {
            HashMap<String, Object> map = goodsListService.goodsList(hashMap);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return s;
        }
        return s;
    }
    /**
     * 条件跟新产品列表
     * */
    @PostMapping("/goods/updateGoodsList")
    public String updateGoodsList(@RequestBody goods goods){
        String message;
        try {
            message = goodsListService.updateGoodsList(goods);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }
    /**
     * 新增产品列表
     * */
    @PostMapping("/goods/insterGoods")
    public String insterGoods(@RequestBody goods goods){
        String message;
        try {
            message = goodsListService.insterGoods(goods);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }
}
