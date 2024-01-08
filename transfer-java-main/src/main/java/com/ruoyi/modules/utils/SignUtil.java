package com.ruoyi.modules.utils;


import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {

    /**
     * 数据处理，然后进行md5加密
     * @param params HashMap参数，在不需要字典集排序是，最好先排好序
     * @param privateApp
     * @param privateKey
     * @param sort 是否字典集排序然后加密
     * */
    public static String createSign(Map<String, ?> params, String privateApp , String privateKey, Boolean sort) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        Map<String, ?> sortParams = params;
        if(sort){
            // 将参数以参数名的字典升序排序
            sortParams = new TreeMap<String, Object>(params);
        }
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, ?> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value =  (entry.getValue()+"").trim();
            if (!StringUtils.isEmpty(value))
                sb.append("&").append(key).append("=").append(value);
        }
        String stringA = sb.toString().replaceFirst("&","");
        String stringSignTemp = privateApp+stringA + privateKey;
        //将签名使用MD5加密并全部字母变为大写
        return md5(stringSignTemp).toUpperCase();
    }

    //md5加密
    public static String md5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(str.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


}
