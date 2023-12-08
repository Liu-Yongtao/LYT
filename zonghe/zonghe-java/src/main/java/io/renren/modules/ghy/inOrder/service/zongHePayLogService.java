package io.renren.modules.ghy.inOrder.service;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;

import java.util.HashMap;

public interface zongHePayLogService {

    //添加用户信息
    void insertZongHe(HashMap<String,String> hashMap, JSONObject map);

    //获取所有统计
    HashMap<String,Object> getCountZongHe(String yearnumber);

    //    根据ID删除
    int delZongHeById(String id,String tableName);

    //删除所有
    int delAllZongHePayLog(String yearnumber);

    //条件获取数据
    HashMap<String, Object>  getSelectZongHelimit(String account, String logno, String money, Integer status, int stat, int end,String yearnumber);


    //条件更新数据
    void updateZongHe(zonghePayLog zonghePayLog);

    zonghePayLog getZonghePayLogById(int id,String yearnumber);
}
