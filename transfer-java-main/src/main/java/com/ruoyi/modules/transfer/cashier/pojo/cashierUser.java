package com.ruoyi.modules.transfer.cashier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 话商
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cashierUser {

    private Integer id;
    private String title;               // 话商名称
    private String userName;                //用户名
    private BigDecimal withdrawMoney;    //用户余额
    private BigDecimal lv;              //费率
    private Integer type;               //资源类型  0网厅1三方
    private String apiKey;              //apikey
    private Integer status;             //商户状态  0关闭1开启
    private String notifyUrl;           //回调地址
    private String ipList;              // ip白名单
    private String remake;                //备注
    private String time;                //创建时间
    private Integer sorts;              //优先级

}
