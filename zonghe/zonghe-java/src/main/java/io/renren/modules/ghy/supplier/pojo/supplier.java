package io.renren.modules.ghy.supplier.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class supplier{
    private Integer id;
    private String sn;;
    private String name;
    private Integer status;             //是否启用|0禁用1启用
    private String contact;
    private String mobile;
    private String address;
    private Integer sex;                //性别|0未知1男2女
    private String set;
    private Integer istj;
    private Integer sorts=-1;
}
