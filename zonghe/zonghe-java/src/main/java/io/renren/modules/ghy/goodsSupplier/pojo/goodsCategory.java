package io.renren.modules.ghy.goodsSupplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("goodsCategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class goodsCategory {
    private Integer id;
    private String name;
    private Integer status;
    private Integer displaycover;
}
