package com.ruoyi.modules.transfer.cashier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.modules.transfer.cashier.pojo.cashierUser;
import com.ruoyi.modules.transfer.cashier.service.impl.cashierUserServiceImpl;
import com.ruoyi.modules.utils.NonceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class cashierUserController {

    @Autowired
    private cashierUserServiceImpl cashierUserService;

    //获取话商列表
    @PostMapping("/transfer/getCashierUserList")
    public String getCashierUserList(@RequestBody HashMap<String,Object> map){
        String s = "";
        try {
            HashMap<String, Object> userList = cashierUserService.getCashierUserList(map);
            ObjectMapper mapper = new ObjectMapper();
            s = mapper.writeValueAsString(userList);
        } catch (JsonProcessingException e) {
            return "未知异常";
        }
        return s;
    }

    @PostMapping("/transfer/insertCashierUser")
    public String insertCashierUser(@RequestBody cashierUser cashierUser){
        int i = 0;
        i = cashierUserService.insertCashierUser(cashierUser);
        return i>=1?"成功":"失败";
    }

    /**
     * 条件更新话商
     * */
    @PostMapping("/transfer/updateCashierUser")
    public String updateCashierUser(@RequestBody cashierUser cashierUser){
        int i = 0;
        i = cashierUserService.updateCashierUser(cashierUser);
        return i>=1?"成功":"失败";
    }

    /**
     * 重置APIKEY
     * */
    @PostMapping("/transfer/restCashierUserApiKey")
    public String restCashierUserApiKey(@RequestBody cashierUser cashierUser){
        int i = 0;
        String apikey = NonceUtil.createNonce(16);
        cashierUser.setApiKey(apikey);
        i = cashierUserService.updateCashierUser(cashierUser);
        return i>=1?"成功,新生成的ApiKey为"+apikey:"失败";
    }

    /**
     * 话商赠款操作
     * @param map 信息
     *            apiKey 用户apiKey
     *            userName 操作人员名
     *            money     金额
     * */
    @PostMapping("/transfer/updateCashierUserMoney")
    public String updateCashierUserMoney(@RequestBody HashMap<String,Object> map){
        if(null == map.get("apiKey") || map.get("apiKey").equals("")){
            return "apiKey错误";
        }
        if(null == map.get("money")){
            return "金额为空";
        }
        int i=0;
        i = cashierUserService.updateCashierUserMoney(map);
        return i>=1?"成功":"失败";
    }

    /**
     * 条件获增减款日志
     * */
    @PostMapping("/transfer/getCashierUserCheckLog")
    public String getCashierUserCheckLog(@RequestBody HashMap<String,Object> map){
        HashMap<String, Object> checkLog = cashierUserService.getCashierUserCheckLog(map);
        ObjectMapper mapper = new ObjectMapper();
        String s="";
        try {
            s = mapper.writeValueAsString(checkLog);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
}
