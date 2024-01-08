package com.ruoyi.modules.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class OutTradeNoGenerator {

    private static int STATIC_ID = 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 同步锁生成唯一ID
     * @param prefix 订单前缀
     * @return String 生成的ID 格式为：prefix + (yyyyMMddHHmmsss)+XXXX
     * */
    public synchronized static String generateOutTradeNo(String prefix) {

        String dateStr = sdf.format(new Date());
        if(STATIC_ID>9999){
            STATIC_ID = -1;
        }
        STATIC_ID++;
        return prefix + dateStr + String.format("%04d", STATIC_ID);
    }

    /**
     * 同步锁生成唯一ID
     * @param prefix 订单前缀
     * @param time 时间戳，在逻辑层如果有time则使用逻辑层时间戳
     * @return String 生成的ID 格式为：prefix + (yyyyMMddHHmmsss)+XXXX
     * */
    public synchronized static String generateOutTradeNo(String prefix,long time) {
        String dateStr = sdf.format(time);
        if(STATIC_ID>9999){
            STATIC_ID = -1;
        }
        STATIC_ID++;
        return prefix + dateStr + String.format("%04d", STATIC_ID);
    }


    /**
     * 同步锁生成唯一ID，时间戳格式
     * @param prefix 订单前缀
     * @param time 时间戳，在逻辑层如果有time则使用逻辑层时间戳
     * @return String 生成的ID 格式为：prefix + (1703647480979)+XXXX
     * */
    public synchronized static String generateTimestamp(String prefix,Long time) {
        if(time == null){
            time = System.currentTimeMillis();
        }
        if(STATIC_ID>9999){
            STATIC_ID = -1;
        }
        STATIC_ID++;
        return prefix + time + String.format("%04d", STATIC_ID);
    }


}
