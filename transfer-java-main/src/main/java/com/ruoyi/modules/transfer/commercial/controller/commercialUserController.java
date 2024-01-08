package com.ruoyi.modules.transfer.commercial.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.modules.transfer.commercial.pojo.commercialUser;
import com.ruoyi.modules.transfer.commercial.service.impl.commercialUserServiceImpl;
import com.ruoyi.modules.utils.NonceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class commercialUserController {

    @Autowired
    private commercialUserServiceImpl commercialUserService;

    //获取话商列表
    @PostMapping("/transfer/getCommercialUserList")
    public String getCommercialUserList(@RequestBody HashMap<String,Object> map){
        String s = "";
        try {
            HashMap<String, Object> userList = commercialUserService.getCommercialUserList(map);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            return "未知异常";
        }
        return s;
    }

    @PostMapping("/transfer/insertCommercialUser")
    public String insertCommercialUser(@RequestBody commercialUser commercialUser){
        int i = 0;
        i = commercialUserService.insertCommercialUser(commercialUser);
        return i>=1?"成功":"失败";
    }

    @PostMapping("/transfer/updateCommercialUser")
    public String updateCommercialUser(@RequestBody commercialUser commercialUser){
        int i = 0;
        i = commercialUserService.updateCommercialUser(commercialUser);
        return i>=1?"成功":"失败";
    }

    /**
     * 重置APIKEY
     * */
    @PostMapping("/transfer/restCommercialUserApiKey")
    public String restCommercialUserApiKey(@RequestBody commercialUser commercialUser){
        int i = 0;
        String apikey = NonceUtil.createNonce(16);
        commercialUser.setApiKey(apikey);
        i = commercialUserService.updateCommercialUser(commercialUser);
        return i>=1?"成功,新生成的ApiKey为"+apikey:"失败";
    }

}
