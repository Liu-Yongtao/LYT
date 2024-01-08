package com.ruoyi.modules.transfer.linkSupplier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocation;
import com.ruoyi.modules.transfer.linkSupplier.service.impl.supplierLocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class supplierLocationController {

    @Autowired
    private supplierLocationServiceImpl supplierLocationService;
    //获取话商列表
    @PostMapping("/transfer/getSupplierLocationList")
    public String getSupplierLocationList(@RequestBody HashMap<String,Object> map){
        String s = "";
        try {
            HashMap<String, Object> userList = supplierLocationService.getSupplierLocationList(map);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            return "未知异常";
        }
        return s;
    }

    /**
     * 新增通道
     * */
    @PostMapping("/transfer/insertSupplierLocation")
    public String insertSupplierLocation(@RequestBody supplierLocation supplierLocation){
        int i = 0;
        i =supplierLocationService.insertSupplierLocation(supplierLocation);
        return i>=1?"成功":"失败";
    }

    /**
     * 更新通道
     * */
    @PostMapping("/transfer/updateSupplierLocation")
    public String updateSupplierLocation(@RequestBody supplierLocation supplierLocation){
        int i = 0;
        i = supplierLocationService.updateSupplierLocation(supplierLocation);
        return i>=1?"成功":"失败";
    }

    @PostMapping("/transfer/getPassageIdAndName")
    public String getPassageIdAndName(){
        String s = "";
        try {
            ArrayList<supplierLocation> idAndName = supplierLocationService.getPassageIdAndName();
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(idAndName);
        } catch (JsonProcessingException e) {
            return "未知异常";
        }
        return s;
    }


}
