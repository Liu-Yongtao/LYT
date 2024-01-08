package com.ruoyi.modules.transfer.commercial.service.impl;

import com.ruoyi.modules.transfer.commercial.mapper.commercialUserMapper;
import com.ruoyi.modules.transfer.commercial.pojo.commercialUser;
import com.ruoyi.modules.transfer.commercial.service.commercialUserService;
import com.ruoyi.modules.utils.NonceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class commercialUserServiceImpl implements commercialUserService {

    @Autowired
    private commercialUserMapper commercialUserMapper;

    @Override
    public HashMap<String, Object> getCommercialUserList(HashMap<String, Object> map) {
        map.forEach((k,v)->{
            if(v.equals(""))
                map.put(k,null);
        });

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commercialUserList",commercialUserMapper.getCommercialUserList(map));
        hashMap.put("sumCount",commercialUserMapper.sumCount(map));
        return hashMap;
    }

    @Override
    public int insertCommercialUser(commercialUser commercialUser) {
        commercialUser.setApiKey(NonceUtil.createNonce(16));
        return commercialUserMapper.insertCommercialUser(commercialUser);
    }

    @Override
    public int updateCommercialUser(commercialUser commercialUser) {
        return commercialUserMapper.updateCommercialUser(commercialUser);
    }
}
