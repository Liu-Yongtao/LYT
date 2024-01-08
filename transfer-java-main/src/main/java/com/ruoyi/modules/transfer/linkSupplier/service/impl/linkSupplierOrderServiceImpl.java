package com.ruoyi.modules.transfer.linkSupplier.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.modules.transfer.commercial.pojo.commercialOrder;
import com.ruoyi.modules.transfer.commercial.pojo.commercialOrderClient;
import com.ruoyi.modules.transfer.linkSupplier.mapper.linkSupplierOrderMapper;
import com.ruoyi.modules.transfer.linkSupplier.mapper.linkSupplierUserMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierOrder;
import com.ruoyi.modules.transfer.linkSupplier.pojo.linkSupplierUser;
import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocationClient;
import com.ruoyi.modules.transfer.linkSupplier.service.linkSupplierOrderService;
import com.ruoyi.modules.utils.OutTradeNoGenerator;
import com.ruoyi.modules.utils.SendHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class linkSupplierOrderServiceImpl implements linkSupplierOrderService {

    @Autowired
    private linkSupplierOrderMapper linkSupplierOrderMapper;

    @Autowired
    private linkSupplierUserMapper linkSupplierUserMapper;


    /**
            * 分页条件获取渠道订单列表
     * @return  HashMap
     *          --linkSupplierOrderList 渠道订单列表
     *          --sun    统计
     *              -userCount
     *              -moneyCount
     * */
    @Override
    public HashMap<String, Object> getLinkSupplierOrderList(HashMap<String, Object> map) {
        map.forEach((key, value) -> {
            if (value.equals("")) {
                map.put(key, null);
            }
        });
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put("linkSupplierOrderList",linkSupplierOrderMapper.getLinkSupplierOrderList(map));
        hashmap.put("sum",linkSupplierOrderMapper.sumLinkSupplierOrder(map));
        return hashmap;
    }

    /**
     * 产码逻辑层，通过传入的渠道列表，遍历渠道列表分别向渠道商发送请求
     * 并将产码状态记录以json保存
     * @param locationList 通道渠道列表
     * @param order 商户订单地址引用
     * */
    @Override
    public LinkedHashMap<String,Object> inLinkSupplierOrder(String locationList, commercialOrder order) {
        ArrayList arrayList = JSONObject.parseObject(locationList, ArrayList.class);
        ArrayList<commercialOrderClient> commercialOrderClients = new ArrayList<>();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("account",order.getAccount());
        map.put("money",order.getMoney());
        map.put("ipaddr",order.getIpaddr());

        for (int i=0;i<arrayList.size();i++) {
            supplierLocationClient client = JSONObject.parseObject(arrayList.get(i) + "", supplierLocationClient.class);
            //判断渠道是否在通道中被禁用了
            if(client.getStatus() == 1){
                linkSupplierUser user = linkSupplierUserMapper.getLinkSupplierUserById(client.getId());
                // 判断是否该渠道是否被禁用
                if(user!=null){
                    linkSupplierOrder linkSupplierOrder = new linkSupplierOrder();
                    commercialOrderClient commercialOrderClient = new commercialOrderClient();
                    //记录开始时间
                    linkSupplierOrder.setStatTime(DateUtils.getTime());
                    //预设失败
                    linkSupplierOrder.setStatus(1);

                    String response = "";
                    try {
                        response = SendHttp.sendCashierPost(map,user.getUrl(),"json");
                    } catch (IOException e) {
                        throw new RuntimeException("系统异常");
                    }
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    if(jsonObject.getInteger("code") == 200){
                        // 成功
                        linkSupplierOrder.setStatus(2);
                        order.setUrlStatus(2);
                        linkSupplierOrder.setPayUrl(jsonObject.getString("payUrl"));
                        linkSupplierOrder.setQueryUrl(jsonObject.getString("queryUrl"));
                        map.clear();
                        map.put("payUrl",jsonObject.getString("payUrl"));
                        map.put("queryUrl",jsonObject.getString("queryUrl"));
                    }
                    linkSupplierOrder.setLogno(order.getLogno());
                    linkSupplierOrder.setIpaddr(order.getIpaddr());
                    linkSupplierOrder.setAccount(order.getAccount());
                    linkSupplierOrder.setMoney(order.getMoney());
                    linkSupplierOrder.setCashierId(order.getCashierId());
                    linkSupplierOrder.setPhoneType(order.getPhoneType());
                    linkSupplierOrder.setZfType(order.getZfType());
                    linkSupplierOrder.setLocation(client.getId());
                    linkSupplierOrder.setTelephoneType(order.getTelephoneType());
                    String no = OutTradeNoGenerator.generateTimestamp("L",null);
                    linkSupplierOrder.setPayLogno(no);
                    String end = DateUtils.getTime();
                    linkSupplierOrder.setEndTime(end);
                    //写入数据库
                    linkSupplierOrderMapper.insertLinkSupplierOrder(linkSupplierOrder);
                    //记录渠道状态
                    commercialOrderClient.setLinkSupplierNo(no);
                    commercialOrderClient.setLocation(client.getId());
                    commercialOrderClient.setUrlStatus(linkSupplierOrder.getStatus());
                    commercialOrderClient.setEndTime(end);
                    commercialOrderClients.add(commercialOrderClient);
                    //判断是否返回
                    if (linkSupplierOrder.getStatus() == 2){
                        break;
                    }
                }
            }
        }
        order.setNode(JSON.toJSONString(commercialOrderClients));
        return map;
    }

}
