package com.ruoyi.modules.transfer.linkSupplier.mapper;

import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface linkSupplierOrderMapper {

    /**
     * 统计渠道订单页的总数和总金额
     * */
    HashMap<String,Object> sumLinkSupplierOrder(HashMap<String,Object> map);

    /**
     * 分页条件获取商户订单列表
     * @return cashierOrderList 商户订单列表
     * */
    List<linkSupplierOrder> getLinkSupplierOrderList(HashMap<String,Object> map);

    /**
     * 插入渠道订单数据
     * */
    int insertLinkSupplierOrder(linkSupplierOrder linkSupplierOrder);

    /**
     * 通过渠道订单号查询订单查单地址
     * */
    String getQueryOrderByPayLogno(String payLogno);

}
