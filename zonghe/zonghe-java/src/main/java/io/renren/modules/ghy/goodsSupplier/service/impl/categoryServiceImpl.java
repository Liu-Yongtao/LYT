package io.renren.modules.ghy.goodsSupplier.service.impl;

import io.renren.modules.ghy.goodsSupplier.dao.categoryMapper;
import io.renren.modules.ghy.goodsSupplier.pojo.cashierCategory;
import io.renren.modules.ghy.goodsSupplier.pojo.goodsCategory;
import io.renren.modules.ghy.goodsSupplier.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class categoryServiceImpl implements categoryService {

    @Autowired
    private categoryMapper categoryMapper;

    @Override
    public List<HashMap<String, Object>> categoryIdAndName() {
        return categoryMapper.categoryIdAndName();
    }

    @Override
    public HashMap<String, Object> cashierCategoryList(String name, Integer status, int staLimit, int endLimit) {
        if(name.equals("")){
            name = null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("cashierCategoryList",categoryMapper.cashierCategoryList(name,status,staLimit,endLimit));
        map.put("sumCount",categoryMapper.sumCount(name,status,"w_cashier_category"));
        return map;
    }

    @Override
    public String insertCashierCategory(cashierCategory cashierCategory) {
        int i;
        try {
            i = categoryMapper.insertCashierCategory(cashierCategory);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public String updateCashierCategory(cashierCategory cashierCategory) {
        int i;
        try {
            i = categoryMapper.updateCashierCategory(cashierCategory);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }


//=============================== 业务类型 ================================

    @Override
    public List<HashMap<String, Object>> goodsCategoryIdAndName() {
        return categoryMapper.goodsCategoryIdAndName();
    }

    @Override
    public HashMap<String, Object> goodsCategoryList(String name, Integer status, int staLimit, int endLimit) {
        if(name.equals("")){
            name = null;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsCategoryList",categoryMapper.goodsCategoryList(name,status,staLimit,endLimit));
        map.put("sumCount",categoryMapper.sumCount(name,status,"w_goods_category"));
        return map;
    }

    @Override
    public String insertgoodsCategory(goodsCategory goodsCategory) {
        int i;
        try {
            i = categoryMapper.insertGoodsCategory(goodsCategory);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }

    @Override
    public String updategoodsCategory(goodsCategory goodsCategory) {
        int i;
        try {
            i = categoryMapper.updateGoodsCategory(goodsCategory);
        } catch (Exception e) {
            throw new RuntimeException("失败");
        }
        if(i==0) return "失败";
        return "成功";
    }
}
