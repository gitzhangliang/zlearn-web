package com.zl.spring.bean.beanfactorypostprocessor;

import com.zl.spring.bean.Bean1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Bean1 b1 = ( Bean1 ) beanFactory.getBean("bean1");
        b1.name = "123";
    }
}
