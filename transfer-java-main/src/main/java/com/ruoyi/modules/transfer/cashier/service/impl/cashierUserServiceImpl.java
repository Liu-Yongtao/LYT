package com.ruoyi.modules.transfer.cashier.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.modules.transfer.cashier.mapper.cashierUserMapper;
import com.ruoyi.modules.transfer.cashier.pojo.cashierUser;
import com.ruoyi.modules.transfer.cashier.pojo.cashierUserCheck;
import com.ruoyi.modules.transfer.cashier.service.cashierUserService;
import com.ruoyi.modules.utils.NonceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class cashierUserServiceImpl implements cashierUserService {

    @Autowired
    private cashierUserMapper cashierUserMapper;


    @Override
    public HashMap<String, Object> getCashierUserList(HashMap<String, Object> map) {
        map.forEach((k,v)->{
            if(v.equals(""))
                map.put(k,null);
        });

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("cashierUserList",cashierUserMapper.getCashierUserList(map));
        hashMap.put("sumCount",cashierUserMapper.sumCount(map));
        return hashMap;
    }

    /**
     * 新增话商
     * */
    @Override
    public int insertCashierUser(cashierUser cashierUser) {
        cashierUser.setApiKey(NonceUtil.createNonce(16));
        return cashierUserMapper.insertCashierUser(cashierUser);
    }

    @Override
    public int updateCashierUser(cashierUser cashierUser) {
        return cashierUserMapper.updateCashierUser(cashierUser);
    }


    @Override
    public int updateCashierUserMoney(HashMap<String,Object> map) {
        BigDecimal money = new BigDecimal(map.get("money") + "");
        BigDecimal z = new BigDecimal(0);
        // 增减款金额为0时，取消操作
        if(money.compareTo(z) == 0){
            return 1;
        }
        //先通过apiKey查询用户值
        cashierUser cashierUser = cashierUserMapper.getCashierUserByApiKey((String) map.get("apiKey"));
        BigDecimal beforeMoney = cashierUser.getWithdrawMoney();
        BigDecimal afterMoney = beforeMoney.add(money);

        cashierUser user = new cashierUser();
        user.setWithdrawMoney(money);
        user.setId(cashierUser.getId());
        // 商户增/扣款
        int i = cashierUserMapper.updateCashierUser(user);

        cashierUserCheck cashierUserCheck = new cashierUserCheck();
        cashierUserCheck.setCashierId(cashierUser.getId());
        cashierUserCheck.setUserName(map.get("userName")+"");
        cashierUserCheck.setMoney(money.abs());
        cashierUserCheck.setBeforeMoney(beforeMoney);
        cashierUserCheck.setAfterMoney(afterMoney);
        cashierUserCheck.setType(money.compareTo(z) < 0 ? 1:0);
        cashierUserCheck.setTime(DateUtils.getTime());
        System.out.println(cashierUserCheck);
        // 添加增/扣款日志
        int i1 = cashierUserMapper.insertCashierUserCheck(cashierUserCheck);
        return i;
    }

    @Override
    public HashMap<String, Object> getCashierUserCheckLog(HashMap<String, Object> map) {
        map.forEach((k,v)->{
            if(v.equals("")){
                map.put(k,null);
            }
        });
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("cashierUserCheckLog",cashierUserMapper.getCashierUserCheckLog(map));
        map1.put("sumCountCheck",cashierUserMapper.sumCountCheck(map));
        return map1;
    }
}
