package com.ruoyi.modules.transfer.task;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import com.ruoyi.modules.transfer.cashier.service.impl.cashierOrderServiceImpl;
import com.ruoyi.modules.transfer.commercial.mapper.commercialOrderMapper;
import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;
import com.ruoyi.modules.transfer.commercial.pojo.commercialOrderClient;
import com.ruoyi.modules.transfer.commercial.service.impl.commercialOrderServiceImpl;
import com.ruoyi.modules.transfer.linkSupplier.mapper.linkSupplierOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class taskScheduled {

    @Autowired
    private cashierOrderServiceImpl cashierOrderService;

    @Autowired
    private commercialOrderServiceImpl commercialOrderService;

    @Autowired
    private commercialOrderMapper commercialOrderMapper;

    @Autowired
    private linkSupplierOrderMapper linkSupplierOrderMapper;

    /**
     * 定时任务，查询订单是否支付成功
     * */
    @Scheduled(cron = "*/30 * * * * ?")
    public void queryCommercialOrder(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("payStatus",0); //支付中
        map.put("urlStatus",2); //产码成功
        map.put("normalStatus",2);  //匹配成功
        RestTemplate restTemplate = new RestTemplate();
        List<commercialOrder> commercialOrderList = commercialOrderMapper.getCommercialOrderByStatus(map);
        for (int i = 0; i < commercialOrderList.size(); i++) {
            commercialOrder commercialOrder = commercialOrderList.get(i);
            ArrayList arrayList = JSONObject.parseObject(commercialOrder.getNode(), ArrayList.class);
            // 一笔商户订单也只有一个产码成功，所以记录的最后一个为产码成功的渠道
            commercialOrderClient client = JSONObject.parseObject(arrayList.get(arrayList.size() - 1) + "", commercialOrderClient.class);
            String queryUrl = linkSupplierOrderMapper.getQueryOrderByPayLogno(client.getLinkSupplierNo());
            //查询话商订单
            cashierOrder cashierOrder = cashierOrderService.getCashierOrderByLogno(commercialOrder.getLogno());

            //查询订单
            String forObject = restTemplate.getForObject(queryUrl, String.class);
            JSONObject jsonObject = JSONObject.parseObject(forObject);
            String msg = jsonObject.getString("msg");

            //完成时间
            String endTime = DateUtils.getTime();
            commercialOrder.setEndTime(endTime);
            cashierOrder.setEndTime(endTime);
            //订单支付成功
            if(msg.equals("success")){
                cashierOrder.setStatus(2);
                cashierOrder.setRemake("充值成功");
                commercialOrder.setPayStatus(2);
                //订单成功，通知话商
                cashierOrderService.successOrderNotify(cashierOrder);
                // 订单成功，通知商户
                commercialOrderService.commercialNotify(commercialOrder);
                continue;
            }
            cashierOrder.setStatus(1);
            cashierOrder.setRemake("充值失败");
            commercialOrder.setPayStatus(1);
            //订单失败通知话商
            cashierOrderService.loseOrderNotify(cashierOrder,false);
            //订单失败通知商户
            commercialOrderService.commercialNotify(commercialOrder);

        }
    }

}
