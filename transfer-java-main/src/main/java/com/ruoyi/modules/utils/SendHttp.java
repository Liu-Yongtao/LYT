package com.ruoyi.modules.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SendHttp {

    /**
     * 发送Http请求方法
     * @Param map 发送的参数Hashmap集合
     * @Param url 请求地址
     * @Param ContentType 请求头类型，form or json
     * */
    public static String sendCashierPost(HashMap<String,?> map, String url, String ContentType) throws IOException {
        HttpPost http = new HttpPost(url);
        String str = "";
        if(ContentType.equals("json")){
            http.setHeader("Content-Type","application/json");
            str = JSONObject.toJSONString(map);
        }else{
            http.setHeader("Content-Type","application/x-www-form-urlencoded");
            str = getFormDataString(map);
        }
        StringEntity stringEntity = new StringEntity(str, Consts.UTF_8);
        http.setEntity(stringEntity);

        RequestConfig build = RequestConfig.custom().
                setSocketTimeout(3000).setConnectTimeout(3000).build();

        http.setConfig(build);

        CloseableHttpClient httpClient = HttpPool.getHttpClient();
        CloseableHttpResponse execute = httpClient.execute(http);
        String s = EntityUtils.toString(execute.getEntity());
        return s;
    }

    //处理form数据方法
    public static String getFormDataString(HashMap<String,?> map) {
        String body = "";
        int flag = 0;
        for (Map.Entry<String,?> entry : map.entrySet()) {
            if (flag != 0) {
                body += "&";
            }
            body += entry.getKey() + "=" + entry.getValue();
            flag++;
        }
        return body;
    }
}
