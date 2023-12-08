package io.renren.modules.ghy.goodsSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;


@Alias("goodsSupplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class goodsSupplier {
    private Integer id;
    private Integer supplierid;
    private Integer goodsid;
    private String sn;
    private BigDecimal price;
    private BigDecimal prevprice;
    private String updatetime;
    private Integer status;
    private String set;


}
