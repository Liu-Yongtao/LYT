package com.ruoyi.modules.transfer.linkSupplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 渠道
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class supplierLocationClient {
    private Integer id;   //渠道id
    private String title;   // 渠道名称
    private Integer type;   //0快充1慢充
    private Integer zfType;     //支付类型
    private Integer phoneType;  //操作系统 0Android 1IOS 2双端
    private Integer status; //渠道状态

}
