package com.ruoyi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * 全局异常处理
 * */
@RestControllerAdvice(basePackages = "com.ruoyi.modules.transfer")
public class catchExceptionConfig {

    private static Logger log = LoggerFactory.getLogger(catchExceptionConfig.class);


    @ExceptionHandler(Exception.class)
    //定义放回
    @ResponseBody
    public HashMap<String,Object> exceptionAfter(Exception e){
        // 获取异常信息，记录日志
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String className = stackTraceElement.getClassName();
        String fileName = stackTraceElement.getFileName();
        int lineNumber = stackTraceElement.getLineNumber();
        String methodName = stackTraceElement.getMethodName();
        log.error("类名:{},文件名:{},行数:{},办法名:{}", className, fileName, lineNumber, methodName);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code",501);
        map.put("message","未知错误，联系管理员");
        return map;
    }


}
