package com.ruoyi.modules.transfer.linkSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 链接供货商pojo类
 * */
@Alias("linkSupplierUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class linkSupplierUser {

    private Integer id;
    private String title;               // 渠道名称
    private String userName;                //用户名
    private Integer type;               //0快充1慢充
    private Integer zfType;            //支付方式
    private Integer phoneType;          //操作系统 0Android 1ISO 2双端
    private BigDecimal lv;              //费率
    private Integer status;             //商户状态  0关闭1开启
    private String url;                 //通信接口
    private String remake;              //备注
    private String time;
    private Integer sorts;              //优先级
}
