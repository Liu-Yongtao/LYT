package io.renren.modules.ghy.supplier.service.impl;

import io.renren.modules.ghy.supplier.dao.locationMapper;
import io.renren.modules.ghy.supplier.pojo.location;
import io.renren.modules.ghy.supplier.service.locationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class locationServiceImpl implements locationService {

    @Autowired
    private locationMapper locationMapper;
    private Logger log = LoggerFactory.getLogger(locationServiceImpl.class);

    /**
     * 获取供货商策略列表
     * supplierList 为供货商策略列表
     * sumCount 总数量
     * */
    @Override
    public HashMap<String,Object> getSupplierLocationList(String name,Integer status,int staLimit, int endLimit) {
        if(name.equals("")){
            name = null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierLocationList",locationMapper.getSupplierLocationList(name,status,staLimit, endLimit));
        map.put("sumCount",locationMapper.supplierLocationCount(name,status));
        return map;
    }

    @Override
    public String insertSupplierLocation(location location) {
        int i = 0;
        try {
            i = locationMapper.insertSupplierLocation(location);
        } catch (Exception e) {
            log.error("供货商策略添加错误",e);
            throw new RuntimeException("失败");
        }
        if(i>0) return "成功";
        return "失败";
    }

    @Override
    public String updateSupplierLocation(location location) {
        int i = 0;
        try {
            i = locationMapper.updateSupplierLocation(location);
        } catch (Exception e) {
            log.error("供货商策略更新错误",e);
            throw new RuntimeException("失败");
        }
        if(i>0) return "成功";
        return "失败";
    }

    /**
     * 根据产品编号 和 商户id获取策略信息
     * @param sn 产品编号
     * @param cashierid 商户id
     * */
    @Override
    public HashMap<String,Integer> locationBySnCashierId(String sn, String cashierid) {
        return locationMapper.locationBySnCashierId(sn,cashierid);
    }


}
