package io.renren.modules.ghy.goodsSupplier.service.impl;

import io.renren.modules.ghy.goodsSupplier.dao.cashierUserMapper;
import io.renren.modules.ghy.goodsSupplier.dao.goodsListMapper;
import io.renren.modules.ghy.goodsSupplier.service.cashierUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class cashierUserServiceImpl implements cashierUserService {

    @Autowired
    private cashierUserMapper cashierUserMapper;

    /**
     * 获取产品列表
     * @return cashierUserList 为产品列表;sumCount为读取行数
     * */
    @Override
    public HashMap<String,Object> cashierUserList(HashMap<String,Object> hashMap) {
        hashMap.forEach((key, value) -> {
            if (hashMap.get(key).equals("")) {
                hashMap.put(key, null);
            }
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("cashierUserList",cashierUserMapper.cashierUserList(hashMap));
        map.put("sumCount",cashierUserMapper.cashierUserCount(hashMap));
        return map;
    }

    /**
     * 校验商户身份
     * @param client_id 身份id
     * @param client_secret 身份密钥
     * */
    @Override
    public HashMap<String, Object> signCashierUser(String client_id, String client_secret) {
        return cashierUserMapper.signCashierUser(client_id,client_secret);
    }
}
