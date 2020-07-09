package com.zl.spring.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Client{
    @Test
    public void bean() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigBean.class);

        BeanA a = ctx.getBean(BeanA.class);
        BeanB b = ctx.getBean(BeanB.class);
        BeanC c = ctx.getBean(BeanC.class);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(c.getBeanB());
        System.out.println(c.getBeanA());

        BeanD d = ctx.getBean(BeanD.class);
        System.out.println(d);
        System.out.println(c.getBeanB().getBeanD());
    }
}