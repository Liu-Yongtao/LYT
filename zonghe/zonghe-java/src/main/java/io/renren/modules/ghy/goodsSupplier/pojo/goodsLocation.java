package io.renren.modules.ghy.goodsSupplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("goodsLocation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class goodsLocation {

    private Integer id;
    private Integer location;
    private Integer cashierid;
    private Integer goodsid;
    private String province;
    private String updatetime;      //生效时间


}
