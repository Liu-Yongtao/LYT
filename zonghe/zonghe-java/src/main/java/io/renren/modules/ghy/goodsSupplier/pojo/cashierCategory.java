package io.renren.modules.ghy.goodsSupplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("cashierCategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class cashierCategory {
    private int id;
    private String name;
    private int status;

}
