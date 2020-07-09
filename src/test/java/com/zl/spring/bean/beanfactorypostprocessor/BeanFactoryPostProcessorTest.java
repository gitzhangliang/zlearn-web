package com.zl.spring.bean.beanfactorypostprocessor;

import com.zl.spring.bean.Bean1;
import com.zl.spring.bean.Bean3;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**BeanFactoryPostProcessor则是存在于容器启动阶段
 * BeanPostProcessor是存在于对象实例化阶段
 * @author tzxx
 * @date 2019/4/15.
 */
public class BeanFactoryPostProcessorTest {
    @Test
    public void beanFactoryPostProcessor(){
        ApplicationContext container = new ClassPathXmlApplicationContext("bean/classpathScanning.xml");
        Bean1 b1= (Bean1)container.getBean("bean1");
        Assert.assertEquals("123",b1.name);
    }
    @Test
    public void beanPostProcessor(){
        ApplicationContext container = new ClassPathXmlApplicationContext("bean/classpathScanning.xml");
        Bean3 b3= (Bean3 )container.getBean("bean3");
        Assert.assertEquals("bean3",b3.name);
    }
}
