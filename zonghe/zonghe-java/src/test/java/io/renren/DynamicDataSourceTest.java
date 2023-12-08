/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren;

import io.renren.modules.ghy.inOrder.service.send.a_sendRoute;
import io.renren.modules.ghy.supplier.service.impl.locationServiceImpl;

import io.renren.modules.ghy.util.OutTradeNoGenerator;
import io.renren.modules.ghy.util.SendHttp;
import io.renren.modules.ghy.util.SignUtil;
import io.renren.service.DynamicDataSourceTestService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import java.util.*;
import java.util.concurrent.CountDownLatch;


/**
 * 多数据源测试
 *
 * @author Mark sunlightcs@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DynamicDataSourceTestService dynamicDataSourceTestService;

    @Autowired
    private locationServiceImpl locationService;

    @Autowired
    private Map<String,a_sendRoute> sendRouteMap;


    @Value("${server.port}")
    int port;
    @Test
    public void test(){
        Long id = 1L;

        dynamicDataSourceTestService.updateUser(id);
        dynamicDataSourceTestService.updateUserBySlave1(id);
        dynamicDataSourceTestService.updateUserBySlave2(id);
    }

    @Test
    public void test01() throws IOException {
        Random random = new Random();
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("type","create");
            map.put("client_id","rIk91MBoLT4r1UmvT8mZ");
            map.put("client_secret","yfXufs37gVKCqieEQpcBJnQGa");
            HashMap<String, Object> body = new HashMap<>();
            body.put("account","182"+random.nextInt(999999));
            body.put("out_logno","202308241509"+random.nextInt(9999));
            body.put("money",248);
            body.put("productsn","MG-PCYDYSHY-NK");
            map.put("body",body);
            String sign = "";
            try {
                sign = SignUtil.createSign(body,(String)map.get("client_id"),(String)map.get("client_secret"),false);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            map.put("sign",sign);
            String json = SendHttp.sendCashierPost(map, "http://localhost:8080/renren-fast/createorder", "json");
            System.out.println(json);
        }
        System.out.println("我完成了，"+(System.currentTimeMillis()-time)/1000);
    }

    @Test
    public void test02() throws InterruptedException {
//        a_sendRoute a_sendRoute = sendRouteMap.get("37");
//        a_sendRoute.handler();
    }

    public static void main(String[] args) throws InterruptedException {

//        SnowFlake snowFlake = new SnowFlake(1);
        Set set = Collections.synchronizedSet(new HashSet());

        CountDownLatch countDownLatch=new CountDownLatch(1000000);
        long s = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    set.add(OutTradeNoGenerator.generateOutTradeNo(""));
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        long e = System.currentTimeMillis();
        System.out.println(set.size()+"  timeOut: "+(e-s));

    }
}

