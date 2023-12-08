package io.renren.modules.ghy.goodsSupplier.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.renren.modules.ghy.goodsSupplier.service.impl.cashierUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class cashierUserController {

    @Autowired
    private cashierUserServiceImpl cashierUserService;

    private Logger log = LoggerFactory.getLogger(cashierUserController.class);

    @PostMapping("/goods/cashierUserList")
    public String cashierUserList(@RequestBody HashMap<String,Object> hashMap){
        String s = "";
        try {
            HashMap<String, Object> map = cashierUserService.cashierUserList(hashMap);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return s;
        }
        return s;
    }
}
