package io.renren.modules.Demo.controller;

import io.renren.modules.Demo.pojo.login;
import io.renren.modules.Demo.service.impl.loginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@RestController
public class controller {

    @Autowired
    private loginServiceImpl loginService;

    private Integer TABBAR_STYLE = 1;

    /**
     * 切换页面，1为充电，0为话费充值
     * */
    @PostMapping("/cd/setTabBarStyle")
    public void setTabBarStyle(@RequestBody Integer style){
         TABBAR_STYLE = style;
    }

    @PostMapping("/text/getTabBarStyle")
    public String getTabBarStyle(){
        return String.valueOf(TABBAR_STYLE);
    }

    /**
     * 登录
     * */
    @PostMapping("/text/cdLogin")
    public String cdLogin(@RequestBody login login){
        try {
            loginService.login(login);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "登录成功";
    }

    /**
     * 注册
     * */
    @PostMapping("/text/cdSignIn")
    public String cdSignIn(@RequestBody login login){
        try {
            loginService.signIn(login);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "注册成功";
    }

}
