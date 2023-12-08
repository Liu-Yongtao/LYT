package io.renren.modules.ghy.supplier.service.impl;

import io.renren.modules.ghy.supplier.dao.supplierListMapper;
import io.renren.modules.ghy.supplier.pojo.supplier;
import io.renren.modules.ghy.supplier.service.supplierListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class supplierListServiceImpl implements supplierListService {

    @Autowired
    private supplierListMapper supplierListMapper;

    /**
     * 获取产品列表
     * @return goodsList 为产品列表;sumCount为读取行数
     * */
    @Override
    public HashMap<String,Object> supplierList(String name,Integer status,int staLimit, int endLimit) {
        if(name.equals("")){
            name = null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("supplierList",supplierListMapper.supplierList(name,status,staLimit, endLimit));
        map.put("sumCount",supplierListMapper.supplierCount(name, status));
        return map;
    }

    @Override
    public synchronized String insertSupplier(supplier supplier) {
        int i;
        int count = supplierListMapper.supplierCount(null,null);
        supplier.setSn(String.format("%08d",count+1));
        try {
            i = supplierListMapper.insertSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public String updateSupplier(supplier supplier) {
        int i;
        try {
            i = supplierListMapper.updateSupplier(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public List<HashMap<String,Object>> getSupplierIdAndName() {
        return supplierListMapper.getSupplierIdAndName();
    }
}
