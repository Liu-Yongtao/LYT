package com.ruoyi.modules.transfer.commercial.mapper;


import com.ruoyi.modules.transfer.commercial.pojo.commercialUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface commercialUserMapper {

    /**
     * 条件获取商户信息列表
     * */
    ArrayList<commercialUser> getCommercialUserList(HashMap<String,Object> map);

    /**
     * 统计满足条件的商户信息列表
     * */
    int sumCount(HashMap<String,Object> map);

    /**
     * 新增用户
     * */
    int insertCommercialUser(commercialUser commercialUser);

    /**
     * 修改用户
     * */
    int updateCommercialUser(commercialUser commercialUser);

    /**
     * 根据Apikey获取商户
     * @return id,passage--通道号,notifyUrl--通知地址
     * */
    commercialUser getCommercialUserByApiKey(String apiKey);

}
