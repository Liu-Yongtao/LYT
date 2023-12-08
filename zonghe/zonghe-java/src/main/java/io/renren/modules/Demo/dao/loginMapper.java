package io.renren.modules.Demo.dao;

import io.renren.modules.Demo.pojo.login;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface loginMapper {

    /**
     * 登录
     * */
    login login(login login);

    /**
     * 注册
     * */
    int signIn(login login);
}
