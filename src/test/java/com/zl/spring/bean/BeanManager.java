package com.zl.spring.bean;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
public class BeanManager {

    private void assertBeanFactory(BeanFactory container){
        Bean1 b1= ( Bean1 )container.getBean("bean1");
        Bean2 b2= ( Bean2 )container.getBean("bean2");
        Assert.assertTrue(b1.register());
        Assert.assertTrue(b2.register());
    }
    /**
     * 代码注入
     */
    @Test
    public void codeRegisterBean(){
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanRegistry);
        assertBeanFactory(container);
    }

    private BeanFactory bindViaCode(DefaultListableBeanFactory registry) {
        AbstractBeanDefinition beanDefinition1 = new RootBeanDefinition(Bean1.class);
        AbstractBeanDefinition beanDefinition2 = new RootBeanDefinition(Bean2.class);
        //bean注册到容器中
        registry.registerBeanDefinition("bean1",beanDefinition1);
        registry.registerBeanDefinition("bean2",beanDefinition2);
        //构造方法注入
        ConstructorArgumentValues argValues = new ConstructorArgumentValues();
        argValues.addIndexedArgumentValue(0, beanDefinition1);
        beanDefinition2.setConstructorArgumentValues(argValues);
        //setter注入
//        MutablePropertyValues propertyValues  = new MutablePropertyValues();
//        propertyValues.addPropertyValue(new PropertyValue("b1",beanDefinition1));
//        beanDefinition2.setPropertyValues(propertyValues);
        return registry;
    }

    /**
     * xml注入
     */
    @Test
    public void xmlRegisterBean(){
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaXMLFile(beanRegistry);
        assertBeanFactory(container);
    }

    public static BeanFactory bindViaXMLFile(BeanDefinitionRegistry registry){
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
        reader.loadBeanDefinitions("bean/bean.xml");
        return ( BeanFactory )registry;
    }


    /**
     * annotation注入
     */
    @Test
    public void annotationRegisterBean(){
        ApplicationContext container = new ClassPathXmlApplicationContext("bean/classpathScanning.xml");
        Bean1 b1= ( Bean1 )container.getBean("bean1");
        Bean3 b3= ( Bean3 )container.getBean("bean3");
        Assert.assertTrue(b1.register());
        Assert.assertTrue(b3.register());
    }
}
