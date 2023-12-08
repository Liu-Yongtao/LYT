package io.renren.modules.Demo.service.impl;

import io.renren.modules.Demo.dao.loginMapper;
import io.renren.modules.Demo.pojo.login;
import io.renren.modules.Demo.service.loginService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginServiceImpl implements loginService {

    @Autowired
    private loginMapper loginMapper;

    @Override
    public void login(login login) {
        login.setPwd(new Sha256Hash(login.getPwd()).toHex());
        login l = loginMapper.login(login);
        if(l==null){
            throw new RuntimeException("账号或密码错误");
        }
    }

    @Override
    public void signIn(login login) {
        login.setPwd(new Sha256Hash(login.getPwd()).toHex());
        int i = 0;
        try {
            i = loginMapper.signIn(login);
        } catch (Exception e) {
            throw new RuntimeException("账号已存在");
        }
        if(i<1){
            throw new RuntimeException("注册失败");
        }
    }
}
