package com.ruoyi.modules.transfer.linkSupplier.service.impl;

import com.ruoyi.modules.transfer.linkSupplier.mapper.supplierLocationMapper;
import com.ruoyi.modules.transfer.linkSupplier.pojo.supplierLocation;
import com.ruoyi.modules.transfer.linkSupplier.service.supplierLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class supplierLocationServiceImpl implements supplierLocationService {

    @Autowired
    private supplierLocationMapper supplierLocationMapper;

    /**
     * 条件获取通道
     * */
    @Override
    public HashMap<String, Object> getSupplierLocationList(HashMap<String, Object> map) {
        map.forEach((k,v)->{
            if(v.equals(""))
                map.put(k,null);
        });

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("supplierLocationList",supplierLocationMapper.getSupplierLocationList(map));
        hashMap.put("sumCount",supplierLocationMapper.sumCount(map));
        return hashMap;
    }

    /**
     * 新增通道
     * */
    @Override
    public int insertSupplierLocation(supplierLocation supplierLocation) {
        return supplierLocationMapper.insertSupplierLocation(supplierLocation);
    }

    /**
     * 更新通道
     * */
    @Override
    public int updateSupplierLocation(supplierLocation supplierLocation) {
        return supplierLocationMapper.updateSupplierLocation(supplierLocation);
    }

    @Override
    public ArrayList<supplierLocation> getPassageIdAndName() {
        return supplierLocationMapper.getPassageIdAndName();
    }

}
