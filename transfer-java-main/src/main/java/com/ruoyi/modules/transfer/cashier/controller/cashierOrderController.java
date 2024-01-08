package com.ruoyi.modules.transfer.cashier.controller;

import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.R;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.modules.transfer.cashier.service.impl.cashierOrderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
public class cashierOrderController {

    @Autowired
    private cashierOrderServiceImpl service;

    @PostMapping("/demo/inCashierOrder")
    public R inCashierOrder(@RequestBody String body){
        LinkedHashMap map = JSONObject.parseObject(body, LinkedHashMap.class);
        if(null == map.get("apiKey") || map.get("apiKey").equals("")){
            return R.error(500,"身份校验失败");
        }
        if(null == map.get("out_logno") || map.get("out_logno").equals("")){
            return R.error(500,"订单号不能为空");
        }
        if(null == map.get("account") || map.get("account").equals("")){
            return R.error(500,"充值号码不能为空");
        }
        if(null == map.get("money")){
            return R.error(500,"金额不能为空");
        }
        if(null == map.get("type")){
            return R.error(500,"渠道类型不能空");
        }
        if(null == map.get("sign") || map.get("sign").equals("")){
            return R.error(500,"请检查sign是否正确");
        }
        try {
            service.inCashierOrder(map);
        } catch (RuntimeException e) {
            return R.error(500,e.getMessage());
        }
        return R.ok();
    }

    /**
     * 获取商户订单列表
     * */
    @PostMapping("/transfer/getCashierOrderList")
    public String getCashierOrderList(@RequestBody HashMap<String,Object> map){
        HashMap<String, Object> cashierOrderList = service.getCashierOrderList(map);
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(cashierOrderList);
        } catch (JsonProcessingException e) {
            return "未知错误";
        }
        return s;
    }

    @GetMapping("/demo/print")
    public void print(HttpServletRequest request){
        System.out.println("RemoteAddr: "+ IpUtils.getIpAddr(request));
        System.out.println("Header-UserAgent: "+request.getHeader(Header.USER_AGENT.toString()));
        System.out.println("UserAgentUtil: "+UserAgentUtil.parse(request.getHeader(Header.USER_AGENT.toString())).getPlatform().toString());
        System.out.println("Remote"+request);
    }

}
