package com.ruoyi.modules.transfer.cashier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;


/**
 * 话商订单页
 * */
@Alias("cashierOrder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cashierOrder {
    private Integer id;
    private Integer cashierId;  //话商ID
    private String outLogno;   //  话商订单号，即综合平台的订单号
    private String logno;       // 平台订单号
    private Integer type;       // 渠道类型0快充1慢充
    private Integer telephoneType;    //运营商类型 0移动1联通2电信
    private String account;     // 账号
    private BigDecimal money;   //  金额
    private BigDecimal sellMoney;   //出售金额
    private BigDecimal costMoney;   //成本金额
    private String area;            //归属地
    private Integer status;     //  0待支付1支付失败2支付成功
    private Integer notifyStatus;    //通知状态 0待通知1通知失败2通知成功
    private Integer normalStatus;   //匹配状态 0待通知1通知失败2通知成功
    private Integer passage;   //  通道编号
    private String notifyUrl;      //通知地址
    private String node;            //节点信息
    private String remake;      //备注
    private String statTime;     //创建时间
    private String endTime;     // 结束时间
}