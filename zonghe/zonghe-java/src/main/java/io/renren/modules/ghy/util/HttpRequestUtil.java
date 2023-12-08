package io.renren.modules.ghy.util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequestUtil {

    public static final Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);

    /**
     * 通用请求格式转换,兼容form和json两种格式
     * @param httpServletRequest
     * @param classModel 为null则返回hashmap格式
     * @return
     */
    public static Object commonHttpRequestParamConvert(HttpServletRequest httpServletRequest, Object classModel) {
        Map<String, String> params = new HashMap<>();
        try {
            Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
            if (requestParams != null && !requestParams.isEmpty()) {
                //form格式
                requestParams.forEach((key, value) -> params.put(key, value[0]));
            } else {
                //json格式
                StringBuilder paramSb = new StringBuilder();
                String str = "";
                BufferedReader br = httpServletRequest.getReader();
                while((str = br.readLine()) != null){
                    paramSb.append(str);
                }
                if (paramSb.length() > 0) {
                    //确保顺序输出
                    JSONObject paramJsonObject = JSONObject.parseObject(paramSb.toString(),Feature.OrderedField);
                    if (paramJsonObject != null && !paramJsonObject.isEmpty()) {
                        paramJsonObject.forEach((key, value) ->{
                            params.put(key, String.valueOf(value));
                        });
                    }
                }
            }

            if(classModel==null){
                return params;
            }
            //将params匹配到对应model中
            Class<?> clz = classModel.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (params.containsKey(field.getName())) { //如果map集合存在与属性名相同的键
                    //判断数据类型
                    if ("java.lang.int".equals(field.getType().getCanonicalName())
                            || "java.lang.Integer".equals(field.getType().getCanonicalName())) {
                        field.set(classModel, Integer.valueOf(params.get(field.getName())));
                    } else if ("java.lang.String".equals(field.getType().getCanonicalName())) {
                        field.set(classModel, params.get(field.getName()));
                    } else if ("java.lang.long".equals(field.getType().getCanonicalName())
                            || "java.lang.Long".equals(field.getType().getCanonicalName())) {
                        field.set(classModel, Long.valueOf(params.get(field.getName())));
                    }else if ("java.lang.double".equals(field.getType().getCanonicalName())
                            || "java.lang.Double".equals(field.getType().getCanonicalName())) {
                        field.set(classModel, Double.valueOf(params.get(field.getName())));
                    } else if ("java.lang.boolean".equals(field.getType().getCanonicalName())
                            || "java.lang.Boolean".equals(field.getType().getCanonicalName())) {
                        field.set(classModel, Boolean.valueOf(params.get(field.getName())));
                    }else if("java.math.BigDecimal".equals(field.getType().getCanonicalName())){
                        field.set(classModel, new BigDecimal(params.get(field.getName())));
                    }
//                    field.set(tempClass, params.get(field.getName())); //把属性值赋予给目标类对应属性
                }
            }
        } catch (Exception e) {
            log.error(httpServletRequest.getRemoteAddr()+"请求接口错误,数据为:"+params.toString());
            throw new RuntimeException("参数格式错误");
        }
        return classModel;
    }
}