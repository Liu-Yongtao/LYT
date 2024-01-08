package com.ruoyi.test;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.modules.transfer.cashier.mapper.cashierOrderMapper;
import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@SpringBootTest
public class Test {

    @Autowired
    private cashierOrderMapper cashierOrderMapper;


    @org.junit.jupiter.api.Test
    public void test(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("money",180);
        map.put("type",1);
        map.put("normalStatus",0);
        map.put("stat",0);
        map.put("end",1);
        List<cashierOrder> cashierOrderList = cashierOrderMapper.getCashierOrderList(map);
        System.out.println(cashierOrderList);
    }


    public static void main(String[] args) {

    }
}
