package io.renren.modules.ghy.goodsSupplier.service;

import java.util.HashMap;

public interface cashierUserService {

    /**
     * 获取商户列表
     * cashierUserList 为产品列表
     * sumCount  为字段数量
     * */
    HashMap<String,Object> cashierUserList(HashMap<String,Object> hashMap);

    /**
     * 获取校验商户
     * */
    HashMap<String, Object> signCashierUser(String client_id,String client_secret);

}
