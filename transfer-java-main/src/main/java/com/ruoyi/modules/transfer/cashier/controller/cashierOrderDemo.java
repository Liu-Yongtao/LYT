package com.ruoyi.modules.transfer.cashier.controller;

import com.ruoyi.common.utils.R;
import com.ruoyi.modules.utils.SendHttp;
import com.ruoyi.modules.utils.SignUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;


/**
 * 模拟进单
 *
 * */
@RestController
public class cashierOrderDemo {

    @PostMapping("/demo/inOrder")
    public R inOrder(@RequestBody LinkedHashMap<String,Object> linkedHashMap){
        try {
            String apiKey = (String) linkedHashMap.get("apiKey");
            linkedHashMap.remove("apiKey");
            String sign = SignUtil.createSign(linkedHashMap,"","&"+apiKey,false);
            linkedHashMap.put("apiKey",apiKey);
            linkedHashMap.put("sign",sign);
            String json = SendHttp.sendCashierPost(linkedHashMap, "http://192.168.2.117:8080/demo/inCashierOrder", "json");
            System.out.println(json);
        } catch (Exception e) {
            return R.error(500,"未知错误");
        }
        return R.ok();
    }

    @PostMapping("/demo/callback")
    public R callBack(@RequestBody String body){
        System.out.println("callbock: "+body);
        return R.ok();
    }

    @PostMapping("/demo/ygPayUrl")
    public R ygPayUrl(@RequestBody LinkedHashMap<String,Object> linkedHashMap){
        System.out.println("Hi,My Name YG.I get : "+linkedHashMap);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payUrl","http://www.baidu.com");
        hashMap.put("queryUrl","http://www.baiduClund.com");
        return R.error(500,"错误啦！！");
    }

    @PostMapping("/demo/yesPayUrl")
    public R yesPayUrl(@RequestBody LinkedHashMap<String,Object> linkedHashMap){
        System.out.println("Hi,My Name YES.I get : "+linkedHashMap);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("payUrl","http://www.mi.com");
        hashMap.put("queryUrl","http://192.168.2.117:8080/demo/queryOrder");
        return R.ok(hashMap);
    }

    @GetMapping("/demo/queryOrder")
    public R queryOrder(@RequestParam LinkedHashMap<String,Object> linkedHashMap){
        Random random = new Random();
        int i = random.nextInt(2);
        String str = i==0?"失败":"成功";
        System.out.println("You can Query??-->"+str+":  "+linkedHashMap);
        return i==0?R.error():R.ok();
    }

}
