package io.renren.modules.ghy.goodsSupplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Alias("cashierUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cashierUser {
    private Integer id;
    private String title;
    private String logo;
    private String name;
    private String mobile;
    private BigDecimal withdrawmoney;
    private BigDecimal closemoney;
    private String clientId;
    private String clientSecret;
    private Integer api;
    private Integer status;
    private String account;
    private String category;
    private BigDecimal sqmoney;
    private String notifyurl;
    private String iplist;
    private String perm;
    private Integer nstatus;
    private Integer version;
    private Integer isArea;
    private Integer isMiaofan;
    private Integer sorts;
    private Integer isMiaoFan1;
    private Integer isMiaoFan2;
    private Integer isMiaoFan3;
    private Integer isMiaoFan4;
    private Integer istj;

}
