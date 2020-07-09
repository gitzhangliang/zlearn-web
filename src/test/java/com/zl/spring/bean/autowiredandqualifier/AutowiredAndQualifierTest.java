package com.zl.spring.bean.autowiredandqualifier;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**@Autowired需要
 * {@link org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor} 为它与IoC容器牵线搭桥一样，
 * JSR250的这些注解也同样需要一个BeanPostProcessor帮助它们实现自身的价值。
 * 这个BeanPostProcessor就是{@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor}，只有将CommonAnnotationBeanPostProcessor添
 * 加到容器，JSR250的相关注解才能发挥作用
 * @author tzxx
 * @date 2019/4/15.
 */
public class AutowiredAndQualifierTest {
    @Test
    public void autowired(){
        ApplicationContext container = new ClassPathXmlApplicationContext("bean/classpathScanning.xml");
        Customer c= (Customer)container.getBean("customer");
        Assert.assertEquals("service2",c.serviceName2());
        Assert.assertEquals("service1",c.serviceName1());
        // nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException:
        // No qualifying bean of type 'com.zl.spring.bean.autowiredandqualifier.Service' available:
        // expected single matching bean but found 2: service1,service2;
    }
}
