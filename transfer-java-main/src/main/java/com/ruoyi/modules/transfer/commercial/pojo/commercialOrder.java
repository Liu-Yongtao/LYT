package com.ruoyi.modules.transfer.commercial.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 商户订单类
 * */
@Alias("commercialOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class commercialOrder {
    private Integer id;
    private Integer commercialId;    //商户ID
    private String commercialNo;    //订单号编号
    private Integer cashierId;       //话商ID
    private String logno;           //平台订单号     --用来关联cashierOrder
    private String account;         //充值账号
    private Integer zfType;         //支付类型
    private Integer type;  //渠道类型 0快充1慢充
    private String area;            //归属地
    private BigDecimal money;   //金额
    private String ipaddr;          //ip地址
    private Integer phoneType;   //手机端类型 0 Android安卓端 1 ios苹果端
    private Integer telephoneType;      //运营商类型0移动1联通2电信
    private String notifyUrl;
    private Integer normalStatus;   //匹配状态 0待匹配1匹配失败2匹配成功
    private Integer urlStatus;      //产码状态
    private Integer payStatus;      //支付状态    0待支付1支付失败2支付成功
    private Integer notifyStatus;   //通知状态  0待通知1通知失败2通知成功
    private Integer passage;        //通道编号
    private Integer locationIndex; //当前渠道索引
    private String node;            //节点信息  存放commercialOrderClient
    private String statTime;    //创建时间
    private String endTime;     //结束时间

}
