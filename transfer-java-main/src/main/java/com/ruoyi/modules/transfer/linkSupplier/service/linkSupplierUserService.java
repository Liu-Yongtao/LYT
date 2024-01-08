package com.ruoyi.modules.transfer.linkSupplier.service;

import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierUser;

import java.util.ArrayList;
import java.util.HashMap;

public interface linkSupplierUserService {

    /**
     * 条件获取渠道信息列表
     * */
    HashMap<String,Object> getLinkSupplierUserList(HashMap<String,Object> map);

    /**
     * 新增
     * */
    int insertLinkSupplierUser(linkSupplierUser linkSupplierUser);

    /**
     * 修改
     * */
    int updateLinkSupplierUser(linkSupplierUser linkSupplierUser);

    /**
     * 获取状态为启用的渠道列表
     * */
    ArrayList<linkSupplierUser> getLinkSupplierUserByStatus();


}
