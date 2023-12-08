package io.renren.modules.ghy.supplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import io.renren.modules.ghy.goodsSupplier.service.impl.goodsListServiceImpl;
import io.renren.modules.ghy.supplier.pojo.supplier;
import io.renren.modules.ghy.supplier.service.impl.supplierListServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class supplierController {


    @Autowired
    private supplierListServiceImpl supplierListService;
    private Logger log = LoggerFactory.getLogger(supplierController.class);

    /**
     * 获取商户列表
     * */
    @PostMapping("/supplier/supplierList")
    public String goodsList(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("name");
        Integer status = jsonObject.getInteger("status");
        Integer staLimit = jsonObject.getInteger("staLimit");
        Integer endLimit = jsonObject.getInteger("endLimit");
        String s = "";
        try {
            HashMap<String, Object> map = supplierListService.supplierList(name,status,staLimit, endLimit);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return s;
        }
        return s;
    }

    /**
     * 条件更新供应商
     * */
    @PostMapping("/supplier/updateSupplier")
    public String updateGoodsList(@RequestBody supplier supplier){
        String message;
        try {
            message = supplierListService.updateSupplier(supplier);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 添加供应商
     * */
    @PostMapping("/supplier/insterSupplier")
    public String insterGoods(@RequestBody supplier supplier){
        String message;
        try {
            message = supplierListService.insertSupplier(supplier);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

    /**
     * 获取供应商的id和名字
     * */
    @PostMapping("/supplier/supplierIdAndName")
    public String supplierIdAndName(){
        List<HashMap<String, Object>> supplierIdAndName = supplierListService.getSupplierIdAndName();
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(supplierIdAndName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

}
