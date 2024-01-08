package com.ruoyi.modules.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class phoneAreaUtil {

    /**
     * 通过号码获取 归属地和运营商类型
     * @param phoneNumber 号码
     * @return map
     *          area -- 归属地
     *          type -- 运营商 0移动1联通2电信
     * */
    public static HashMap<String,Object> getPhoneAreaAndType(String phoneNumber){
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("https://cx.shouji.360.cn/phonearea.php?number="+phoneNumber, String.class);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONObject data = JSONObject.parseObject(jsonObject.getString("data"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("area",data.getString("province"));
        map.put("type",data.getString("sp").equals("移动")?0:data.getString("sp").equals("联通")?1:2);
        return map;
    }
}
