package com.ruoyi.modules.transfer.cashier.mapper;

import com.ruoyi.modules.transfer.cashier.pojo.cashierUser;
import com.ruoyi.modules.transfer.cashier.pojo.cashierUserCheck;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface cashierUserMapper {

    /**
     * 条件获取话商信息列表
     * */
    ArrayList<cashierUser> getCashierUserList(HashMap<String,Object> map);

    /**
     * 统计满足条件的信息列表
     * */
    int sumCount(HashMap<String,Object> map);

    /**
     * 新增用户
     * */
    int insertCashierUser(cashierUser cashierUser);

    /**
     * 更新用户
     * */
    int updateCashierUser(cashierUser cashierUser);

    /**
     * 通过查询apiKey 获取用户信息
     * @return cashierUser
     *          -id
     *          -notifyUrl
     *          -lv
     *          -ipList
     *          -withdrawMoney
     * */
    cashierUser getCashierUserByApiKey(String apikey);


    /**
     * 增减款操作
     * */
    int insertCashierUserCheck(cashierUserCheck cashierUserCheck);


    /**
     * 条件获取话商增减款信息
     */
    ArrayList<cashierUserCheck> getCashierUserCheckLog(HashMap<String,Object> map);

    /**
     *
     * 统计条件获取话商增减款信息
     * */
    long sumCountCheck(HashMap<String,Object> map);
}
