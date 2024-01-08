package com.ruoyi.modules.transfer.cashier.service.listener;

import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import com.ruoyi.modules.transfer.cashier.service.impl.cashierOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;



/**
 * 监听过期订单号
 * */
@Service
public class cashierOrderListener implements MessageListener {

    @Autowired
    private cashierOrderServiceImpl cashierOrderService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String[] split = new String(message.getBody()).split(":");
        if(!split[0].equals("order")){
            return;
        }
        //获取话商订单
        cashierOrder cashierOrder = cashierOrderService.getCashierOrderByLogno(split[1]);
        cashierOrder.setRemake(cashierOrder.getNormalStatus() == 2?"匹配成功，订单超时":"订单超时");
        if(cashierOrder.getNormalStatus() == 0){
            cashierOrder.setNormalStatus(1);
        }
        //订单失败统一回调
        cashierOrderService.loseOrderNotify(cashierOrder,true);

    }
}
