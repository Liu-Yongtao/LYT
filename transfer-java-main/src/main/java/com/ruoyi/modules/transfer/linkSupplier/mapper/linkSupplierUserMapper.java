package com.ruoyi.modules.transfer.linkSupplier.mapper;

import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface linkSupplierUserMapper {

    /**
     * 条件获取渠道商信息列表
     * */
    ArrayList<linkSupplierUser> getLinkSupplierUserList(HashMap<String,Object> map);

    /**
     * 统计满足条件的信息列表
     * */
    int sumCount(HashMap<String,Object> map);

    /**
     * 新增用户
     * */
    int insertLinkSupplierUser(linkSupplierUser linkSupplierUser);

    /**
     * 修改用户
     * */
    int updateLinkSupplierUser(linkSupplierUser linkSupplierUser);

    /**
     * 获取状态为启用的渠道列表
     * */
    ArrayList<linkSupplierUser> getLinkSupplierUserByStatus();

    /**
     * 通过渠道ID获取渠道
     * */
    linkSupplierUser getLinkSupplierUserById(int id);

}
