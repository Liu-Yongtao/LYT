package io.renren.modules.ghy.goodsSupplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.renren.modules.ghy.goodsSupplier.pojo.cashierCategory;
import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.goodsSupplier.pojo.goodsCategory;
import io.renren.modules.ghy.goodsSupplier.service.impl.categoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class categoryController {

    @Autowired
    private categoryServiceImpl categoryService;

    /**
     * 获取产品类型id和name
     * */
    @PostMapping("/goods/categoryIdAndName")
    public String categoryIdAndName(){
        List<HashMap<String, Object>> hashMaps = categoryService.categoryIdAndName();
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(hashMaps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取产品类型列表
     * */
    @PostMapping("/goods/cashierCategoryList")
    public String cashierCategoryList(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("name");
        Integer status = jsonObject.getInteger("status");
        Integer staLimit = jsonObject.getInteger("staLimit");
        Integer endLimit = jsonObject.getInteger("endLimit");
        HashMap<String, Object> map = categoryService.cashierCategoryList(name, status, staLimit, endLimit);
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * 条件跟新产品类型
     * */
    @PostMapping("/goods/updateCashierCategory")
    public String updateCashierCategory(@RequestBody cashierCategory cashierCategory){
        String message;
        try {
            message = categoryService.updateCashierCategory(cashierCategory);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 新增产品类型
     * */
    @PostMapping("/goods/insertCashierCategory")
    public String insertCashierCategory(@RequestBody cashierCategory cashierCategory){
        String message;
        try {
            message = categoryService.insertCashierCategory(cashierCategory);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

//============================== 业务类型 ================================
    /**
     * 获取产品类型id和name
     * */
    @PostMapping("/goods/goodsCategoryIdAndName")
    public String goodsCategoryIdAndName(){
        List<HashMap<String, Object>> hashMaps = categoryService.goodsCategoryIdAndName();
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(hashMaps);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 获取业务类型列表
     * */
    @PostMapping("/goods/goodsCategoryList")
    public String goodsCategoryList(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("name");
        Integer status = jsonObject.getInteger("status");
        Integer staLimit = jsonObject.getInteger("staLimit");
        Integer endLimit = jsonObject.getInteger("endLimit");
        HashMap<String, Object> map = categoryService.goodsCategoryList(name, status, staLimit, endLimit);
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * 条件跟新产品类型
     * */
    @PostMapping("/goods/updateGoodsCategory")
    public String updateGoodsCategory(@RequestBody goodsCategory goodsCategory){
        String message;
        try {
            message = categoryService.updategoodsCategory(goodsCategory);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 新增产品类型
     * */
    @PostMapping("/goods/insertGoodsCategory")
    public String insertGoodsCategory(@RequestBody goodsCategory goodsCategory){
        String message;
        try {
            message = categoryService.insertgoodsCategory(goodsCategory);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }
}
