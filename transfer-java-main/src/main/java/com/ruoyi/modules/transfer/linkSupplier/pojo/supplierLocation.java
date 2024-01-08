package com.ruoyi.modules.transfer.linkSupplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * 链接供货商通道类
 * */
@Alias("supplierLocation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class supplierLocation {
    private Integer id;
    private String title;   //通道名称
    private Integer type;   //0快充1慢充2混合
    private Integer zfType;   //支付方式 0微信1支付宝
    private Integer phoneType;  // 操作系统 0Android 1IOS 2双端
    private Integer status;     //通道状态
    private String locationList;    //渠道列表  supplierLocationClient
    private String remake;          //备注
}