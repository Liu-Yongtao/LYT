package com.ruoyi.modules.transfer.linkSupplier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.modules.transfer.linkSupplier.service.impl.linkSupplierOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class linkSupplierOrderController {

    @Autowired
    private linkSupplierOrderServiceImpl linkSupplierOrderService;
    /**
     * 获取商户订单列表
     * */
    @PostMapping("/transfer/getLinkSupplierOrderList")
    public String getLinkSupplierOrderList(@RequestBody HashMap<String,Object> map){
        HashMap<String, Object> cashierOrderList = linkSupplierOrderService.getLinkSupplierOrderList(map);
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(cashierOrderList);
        } catch (JsonProcessingException e) {
            return "未知错误";
        }
        return s;
    }
}
