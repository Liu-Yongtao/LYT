package com.ruoyi.modules.transfer.commercial.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commercialOrderClient {
    private Integer location;   //渠道编号
    private String linkSupplierNo;  //渠道单号
    private Integer urlStatus;  //产图状态  0待产图1失败2成功
    private String endTime;     //业务完成时间

}
