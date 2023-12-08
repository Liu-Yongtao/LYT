package io.renren.modules.ghy.goodsSupplier.dao;

import io.renren.modules.ghy.goodsSupplier.pojo.cashierUser;
import io.renren.modules.ghy.goodsSupplier.pojo.goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface cashierUserMapper {

    /**
     * 获取产品列表
     * @return  为产品列表
     * */
    List<cashierUser> cashierUserList(HashMap<String,Object> hashMap);

    /**
     * 获取总数量
     * */
    int cashierUserCount(HashMap<String,Object> hashMap);

    /**
     * 新增商户
     * */
    int insertCashierUser(cashierUser cashierUser);

    /**
     * 条件更新商户
     * */
    int updateCashierUser(cashierUser cashierUser);

    /**
     * 获取校验商户
     * */
    HashMap<String,Object> signCashierUser(String client_id,String client_secret);
}
