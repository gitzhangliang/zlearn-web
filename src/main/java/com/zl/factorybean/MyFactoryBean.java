package com.zl.factorybean;

import com.zl.domain.Coder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author zhangliang
 * @date 2020/4/26.
 */
@Component
public class MyFactoryBean implements FactoryBean<Coder> {
    @Override
    public Coder getObject() throws Exception {
        Coder coder = new Coder();
        coder.setName("MyFactoryBean_coder");
        return coder;
    }

    @Override
    public Class<?> getObjectType() {
        return Coder.class;
    }
}
