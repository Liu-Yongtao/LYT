package com.ruoyi.modules.transfer.commercial.controller;

import com.ruoyi.common.utils.R;
import com.ruoyi.modules.utils.SendHttp;
import com.ruoyi.modules.utils.SignUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
public class commercialOrderDemo {
    @PostMapping("/demo/inOrder2")
    public R inOrder2(@RequestBody LinkedHashMap<String,Object> linkedHashMap){
        try {
            String apiKey = (String) linkedHashMap.get("apiKey");
            linkedHashMap.remove("apiKey");
            String sign = SignUtil.createSign(linkedHashMap,"","&"+apiKey,false);
            linkedHashMap.put("apiKey",apiKey);
            linkedHashMap.put("sign",sign);
            String json = SendHttp.sendCashierPost(linkedHashMap, "http://192.168.2.117:8080/demo/inCommercialOrder", "json");
            System.out.println(json);
        } catch (Exception e) {
            return R.error(500,"未知错误");
        }
        return R.ok();
    }
}
