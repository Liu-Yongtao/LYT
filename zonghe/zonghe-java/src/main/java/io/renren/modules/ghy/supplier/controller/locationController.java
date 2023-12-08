package io.renren.modules.ghy.supplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.renren.modules.ghy.supplier.pojo.location;
import io.renren.modules.ghy.supplier.service.impl.locationServiceImpl;
import oracle.jdbc.proxy.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class locationController {

    @Autowired
    private locationServiceImpl locationService;

    private Logger log = LoggerFactory.getLogger(locationController.class);


    @PostMapping("/supplier/locationList")
    public String locationList(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String name = jsonObject.getString("name");
        Integer status = jsonObject.getInteger("status");
        Integer staLimit = jsonObject.getInteger("staLimit");
        Integer endLimit = jsonObject.getInteger("endLimit");
        String s = "";
        try {
            HashMap<String, Object> map = locationService.getSupplierLocationList(name,status,staLimit, endLimit);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return s;
        }
        return s;
    }

    @PostMapping("/supplier/insertSupplierLocation")
    public String insertSupplierLocation(@RequestBody location location){
        String message = "失败";
        try {
            message = locationService.insertSupplierLocation(location);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }

    @PostMapping("/supplier/updateSupplierLocation")
    public String updateSupplierLocation(@RequestBody location location){
        String message = "失败";
        try {
            message = locationService.updateSupplierLocation(location);
        } catch (Exception e) {
            return e.getMessage();
        }
        return message;
    }
}
