package io.renren.modules.Demo.service;

import io.renren.modules.Demo.pojo.login;

public interface loginService {
    /**
     * 登录
     * */
    void login(login login);

    /**
     * 注册
     * */
    void signIn(login login);
}
