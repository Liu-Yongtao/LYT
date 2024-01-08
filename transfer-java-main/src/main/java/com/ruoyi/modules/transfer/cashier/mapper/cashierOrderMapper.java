package com.ruoyi.modules.transfer.cashier.mapper;

import com.ruoyi.modules.transfer.cashier.pojo.cashierOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface cashierOrderMapper {


    /**
     * 统计话商订单页的总数和总金额
     * */
    HashMap<String,Object> sumCashierOrder(HashMap<String,Object> map);

    /**
     * 分页条件获取话商订单列表
     * @return cashierOrderList 商户订单列表
     * */
    List<cashierOrder> getCashierOrderList(HashMap<String,Object> map);

    /**
     * 写入订单
     * */
    int inCashierOrder(cashierOrder cashierOrder);

    /**
     * 更新订单
     * */
    int updateCashierOrder(cashierOrder cashierOrder);

    /**
     * 通过平台订单号获取话商订单
     * */
    cashierOrder getCashierOrderByLogno(String logno);

}
