package io.renren.modules.ghy.util;

import com.alibaba.fastjson.JSONObject;
import io.renren.RenrenApplication;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;

public class CommomUtil {

    /**
     * 获取当前项目域名端口号路径
     * */
    public static String getServerUrl(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()+request.getContextPath();
    }

    /**
     * 获取当前项目所在路径
     * */
    public static String getApplicationUrl(){
        ApplicationHome applicationHome = new ApplicationHome(RenrenApplication.class);
        return applicationHome.getDir()+"";
    }

    /**
     * 根据时间格式命名，或者获取当前时间
     * @Param beforeName 时间格式前的名字
     * @Param timeType 时间格式 如“YYYY-MM-DD HH:mm:ss”
     * @Param time 时间戳
     * @Param afterName 时间格式后的名字
     * */
    public static String getTimeorTableName(String beforeName,String timeType,long time,String afterName){
        if(time==-1){
            time = System.currentTimeMillis();
        }
        SimpleDateFormat format = new SimpleDateFormat(timeType);

        return beforeName+format.format(time)+afterName;
    }


    /**
     * 将json转为k=v键值对格式
     * */
    public static String getJsonToString(JSONObject jsonObject,String... body){
        String str = "";
        for (String s : body) {
            str += s+"\n";
        }
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            str += entry.getKey()+": "+entry.getValue()+"\n";
        }
        return str;
    }
}
