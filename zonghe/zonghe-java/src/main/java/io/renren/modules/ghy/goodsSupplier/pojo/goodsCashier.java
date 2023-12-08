package io.renren.modules.ghy.goodsSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;


@Alias("goodsCashier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class goodsCashier {
    private Integer id;
    private Integer cashierid;
    private Integer goodsid;
    private BigDecimal price;       //采购价
    private BigDecimal prevprice;   //上次采购价
    private String updatetime;
    private Integer status;
    private String province;
}
