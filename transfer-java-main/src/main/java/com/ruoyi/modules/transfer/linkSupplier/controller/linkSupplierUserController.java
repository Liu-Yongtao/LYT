package com.ruoyi.modules.transfer.linkSupplier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierUser;
import com.ruoyi.modules.transfer.linkSupplier.service.impl.linkSupplierUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class linkSupplierUserController {

    @Autowired
    private linkSupplierUserServiceImpl linkSupplierUserServiceimpl;

    //获取供应商列表
    @PostMapping("/transfer/getLinkSupplierUserList")
    public String getLinkSupplierUserList(@RequestBody HashMap<String,Object> map){
        String s = "";
        try {
            HashMap<String, Object> userList = linkSupplierUserServiceimpl.getLinkSupplierUserList(map);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            return "未知异常";
        }
        return s;
    }

    /**
     * 新增
     * */
    @PostMapping("/transfer/insertLinkSupplierUser")
    public String insertLinkSupplierUser(@RequestBody linkSupplierUser linkSupplierUser){
        int i = 0;
        i = linkSupplierUserServiceimpl.insertLinkSupplierUser(linkSupplierUser);
        return i>=1?"成功":"失败";
    }

    /**
     * 更新
     * */
    @PostMapping("/transfer/updateLinkSupplierUser")
    public String updateLinkSupplierUser(@RequestBody linkSupplierUser linkSupplierUser){
        int i = 0;
        i = linkSupplierUserServiceimpl.updateLinkSupplierUser(linkSupplierUser);
        return i>=1?"成功":"失败";
    }

    /**
     * 获取状态为启用的渠道列表
     * */
    @PostMapping("/transfer/getLinkSupplierUserByStatus")
    public String getLinkSupplierUserByStatus(){
        String s = "";
        try {
            ArrayList<linkSupplierUser> user = linkSupplierUserServiceimpl.getLinkSupplierUserByStatus();
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return "";
        }
        return s;
    }

}
