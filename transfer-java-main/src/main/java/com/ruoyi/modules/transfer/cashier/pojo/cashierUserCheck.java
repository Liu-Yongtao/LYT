package com.ruoyi.modules.transfer.cashier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Alias("cashierUserCheck")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class cashierUserCheck {
    private Integer id;
    private Integer cashierId;              //"商户ID"
    private String userName;           //"操作人员"
    private Integer type;               //"增/减款|0增1减"
    private BigDecimal beforeMoney;           //"账变前余额"
    private BigDecimal money;                  //"账变金额"
    private BigDecimal afterMoney;            //"账变后余额"
    private String time;                //"创建时间"
}
