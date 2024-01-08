package com.ruoyi.modules.transfer.commercial.mapper;

import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface commercialOrderMapper {

    /**
     * 统计商户订单页的总数和总金额
     * */
    HashMap<String,Object> sumCommercialOrder(HashMap<String,Object> map);

    /**
     * 分页条件获取商户订单列表
     * @return cashierOrderList 商户订单列表
     * */
    List<commercialOrder> getCommercialOrderList(HashMap<String,Object> map);

    /**
     * 商户进单
     * */
    int insertCommercialOrder(commercialOrder commercialOrder);


    /**
     * 更新商户订单
     * */
    int updateCommercialOrder(commercialOrder commercialOrder);

    /**
     * 根据状态查询商户订单
     * */
    List<commercialOrder> getCommercialOrderByStatus(HashMap<String,Object> hashMap);

}
