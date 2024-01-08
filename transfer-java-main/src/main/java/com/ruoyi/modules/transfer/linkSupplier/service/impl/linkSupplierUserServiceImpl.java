package com.ruoyi.modules.transfer.linkSupplier.service.impl;

import com.ruoyi.modules.transfer.linkSupplier.mapper.linkSupplierUserMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierUser;
import com.ruoyi.modules.transfer.linkSupplier.service.linkSupplierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class linkSupplierUserServiceImpl implements linkSupplierUserService {

    @Autowired
    private linkSupplierUserMapper linkSupplierUserMapper;

    /**
     * 获取渠道用户列表
     * */
    @Override
    public HashMap<String, Object> getLinkSupplierUserList(HashMap<String, Object> map) {
        map.forEach((k,v)->{
            if(v.equals(""))
                map.put(k,null);
        });

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("linkSupplierUserList",linkSupplierUserMapper.getLinkSupplierUserList(map));
        hashMap.put("sumCount",linkSupplierUserMapper.sumCount(map));
        return hashMap;
    }

    /**
     * 新增渠道用户
     * */
    @Override
    public int insertLinkSupplierUser(linkSupplierUser linkSupplierUser) {
        return linkSupplierUserMapper.insertLinkSupplierUser(linkSupplierUser);
    }

    /**
     * 更新渠道用户
     * */
    @Override
    public int updateLinkSupplierUser(linkSupplierUser linkSupplierUser) {
        return linkSupplierUserMapper.updateLinkSupplierUser(linkSupplierUser);
    }

    /**
     * 获取已启用的供应商
     * */
    @Override
    public ArrayList<linkSupplierUser> getLinkSupplierUserByStatus() {
        return linkSupplierUserMapper.getLinkSupplierUserByStatus();
    }

}
