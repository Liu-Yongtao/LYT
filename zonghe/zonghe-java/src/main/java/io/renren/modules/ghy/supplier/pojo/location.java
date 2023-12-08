package io.renren.modules.ghy.supplier.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class location {
    private Integer id;
    private String name;
    private Integer categoryid;
    private Integer supplierid1;
    private Integer supplierid2;
    private Integer supplierid3;
    private Integer supplierid4;
    private Integer supplierid5;
    private Integer supplierid6;
    private Integer supplierid7;
    private Integer supplierid8;
    private Integer status;
    private Integer sorts=-1;
}
