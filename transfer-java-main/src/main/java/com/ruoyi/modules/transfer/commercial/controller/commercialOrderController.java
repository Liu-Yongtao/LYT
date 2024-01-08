package com.ruoyi.modules.transfer.commercial.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.R;
import com.ruoyi.modules.transfer.commercial.service.impl.commercialOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
public class commercialOrderController {

    @Autowired
    private commercialOrderServiceImpl commercialOrderService;

    private static Logger log = LoggerFactory.getLogger(commercialOrderController.class);

    /**
     * 商户进单
     * */
    @PostMapping("/demo/inCommercialOrder")
    public R inCommercialOrder(@RequestBody LinkedHashMap<String,Object> map){
        if(null == map.get("apiKey") || map.get("apiKey").equals("")){
            return R.error(500,"身份校验失败");
        }
        if(null == map.get("zfType")){
            return R.error(500,"支付类型不能为空");
        }
        if(null == map.get("ipaddr") || map.get("ipaddr").equals("")){
            return R.error(500,"ip不可为空");
        }
        if(null == map.get("money")){
            return R.error(500,"金额不能为空");
        }
        if(null == map.get("sign") || map.get("sign").equals("")){
            return R.error(500,"缺少校验信息");
        }
        String payUrl = "";
        try {
            payUrl = commercialOrderService.inCommercialOrder(map);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return R.error(500,e.getMessage());
        }catch (Exception e){
            return R.error(500,"未知错误");
        }

        return R.ok(200,payUrl);
    }

    /**
     * 获取商户订单列表
     * */
    @PostMapping("/transfer/getCommercialOrderList")
    public String getCommercialOrderList(@RequestBody HashMap<String,Object> map){
        HashMap<String, Object> cashierOrderList = commercialOrderService.getCommercialOrderList(map);
        ObjectMapper mapper = new ObjectMapper();
        String s = "";
        try {
            s = mapper.writeValueAsString(cashierOrderList);
        } catch (JsonProcessingException e) {
            return "未知错误";
        }
        return s;
    }

}
