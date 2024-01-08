package com.ruoyi.modules.transfer.commercial.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;


/**
 * 商户实体类
 * */
@Alias("commercialUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class commercialUser {
    private Integer id;
    private String title;               // 商户名称
    private String userName;            //用户名
    private String name;                //联系人
    private String apiKey;
    private Integer status;             //商户状态  0关闭1开启
    private String notifyUrl;           //回调地址
    private String ipList;              // ip白名单
    private String time;
    private Integer passage;            //通道号
    private String remake;
    private Integer sorts;              //优先级
}
