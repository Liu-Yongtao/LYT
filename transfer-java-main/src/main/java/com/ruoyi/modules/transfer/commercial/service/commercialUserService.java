package com.ruoyi.modules.transfer.commercial.service;

import com.ruoyi.modules.transfer.commercial.pojo.commercialUser;

import java.util.HashMap;

public interface commercialUserService {

    /**
     * 条件获取商户信息列表
     * */
    HashMap<String,Object> getCommercialUserList(HashMap<String,Object> map);

    /**
     * 新增用户
     * */
    int insertCommercialUser(commercialUser commercialUser);

    /**
     * 修改用户
     * */
    int updateCommercialUser(commercialUser commercialUser);
}
