package io.renren.modules.ghy.inOrder.dao;


import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface zongHePayLogMapper {

//    获取一张表的id最大值，根据字段获取，因为有些表id不是索引
    int getTableMaxId(String tableName,String columnName);

    //添加用户信息
    void insertZongHe(zonghePayLog zonghePayLog);

    //获取所有统计
    HashMap<String,Object> getCountZongHe(String tableName);

//    根据ID删除
    int delZongHeById(String id,String tableName);

    //删除所有
    int delAllZongHePayLog(String tableName);

    //条件获取数据
    List<zonghePayLog> getAllZongHelimit(String account,String logno,String money,Integer status,int stat,int end,String tableName);

    //统计数据
    int getSelectZongHelimit(String account,String logno,String money,Integer status,String tableName);

    //条件更新数据
    void updateZongHe(zonghePayLog zonghePayLog);

    zonghePayLog getZonghePayLogById(int id,String tableName);

    //表自增id赋值为1
    void alterAutoInt(String tableName);
}
