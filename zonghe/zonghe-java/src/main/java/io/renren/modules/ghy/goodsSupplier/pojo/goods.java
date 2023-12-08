package io.renren.modules.ghy.goodsSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Alias("goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class goods {
    private Integer id;
    private String sn;              //商品编码
    private String name;            //产品名称
    private BigDecimal price;       //产品面值
    private Integer categoryid;         //业务类型
    private Integer fromid;
    private Integer status;             //商品状态|0未上架1已上架
}