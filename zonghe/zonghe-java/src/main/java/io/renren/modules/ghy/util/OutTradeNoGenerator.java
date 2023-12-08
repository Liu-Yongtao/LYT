package io.renren.modules.ghy.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class OutTradeNoGenerator {

    private static int STATIC_ID = 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 同步锁生成唯一ID
     * @Param prefix 订单前缀
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
     * @Param prefix 订单前缀
     * @Param time 时间戳，在逻辑层如果有time则使用逻辑层时间戳
     * */
    public synchronized static String generateOutTradeNo(String prefix,long time) {
        String dateStr = sdf.format(time);
        if(STATIC_ID>9999){
            STATIC_ID = -1;
        }
        STATIC_ID++;
        return prefix + dateStr + String.format("%04d", STATIC_ID);
    }
}
