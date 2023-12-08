package io.renren.modules.ghy.inOrder.service.send;

import io.renren.modules.ghy.supplier.dao.supplierListMapper;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("all")
/**
 * 策略抽象类
 * 自动注入到map里，增加渠道时只需继承该类重写方法即可
 * @handler() 总的处理方法
 *
 * */
public abstract class a_sendRoute {

    @Autowired
    supplierListMapper supplierListMapper;

    /**
     * 总的处理方法
     * */
    public void handler() {
        throw new RuntimeException("错误");
    }

}
