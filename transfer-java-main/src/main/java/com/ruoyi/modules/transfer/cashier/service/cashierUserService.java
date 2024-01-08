package com.ruoyi.modules.transfer.cashier.service;

import com.ruoyi.modules.transfer.cashier.pojo.cashierUser;

import java.math.BigDecimal;
import java.util.HashMap;

public interface cashierUserService {

    /**
     * 条件获取话商信息列表
     * */
    HashMap<String,Object> getCashierUserList(HashMap<String,Object> map);

    /**
     * 新增用户
     * */
    int insertCashierUser(cashierUser cashierUser);

    /**
     * 修改用户
     * */
    int updateCashierUser(cashierUser cashierUser);

    /**
     * 增减款操作
     * */
    int updateCashierUserMoney(HashMap<String,Object> map);

    /**
     *  条件获取话商增减款日志
     */
    HashMap<String,Object> getCashierUserCheckLog(HashMap<String,Object> map);
}
