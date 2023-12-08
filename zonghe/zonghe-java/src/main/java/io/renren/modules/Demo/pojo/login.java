package io.renren.modules.Demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("cdLogin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class login {
    private String phone;
    private String pwd;
}
