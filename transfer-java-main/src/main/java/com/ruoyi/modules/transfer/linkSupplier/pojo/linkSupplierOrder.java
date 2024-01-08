package com.ruoyi.modules.transfer.linkSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;


/**
 *  链接供货商订单类
 * */
@Alias("linkSupplierOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class linkSupplierOrder {

    private Integer id;
    private Integer cashierId;  //话商ID
    private String logno;       //平台订单号   --用来关联cashierOrder
    private String payLogno;    //支付订单号
    private String account;     //号码
    private Integer zfType;     //支付类型 0微信1支付宝
    private Integer telephoneType;    //运营商类型 0移动1联通2电信
    private String ipaddr;          //ip地址
    private BigDecimal money;   //金额
    private Integer phoneType;  //手机端类型 0 Android安卓端 1 ios苹果端
    private Integer status;     //产码状态  0产码中1产码失败2产码成功
    private String payUrl;      //支付链接
    private String queryUrl;    //查单链接
    private Integer location;   //当前渠道编号
    private String statTime;        //开启时间
    private String endTime;         //完成时间
}
