package com.zl.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author tzxx
 * @date 2019/5/9.
 */
@Configuration
@Import(BeanA.class)
public class ConfigBeanC {
    @Bean
    public BeanC getBeanC(BeanB beanB, BeanA beanA){
        BeanC beanC = new BeanC();
        beanC.setBeanB(beanB);
        beanC.setBeanA(beanA);
        return beanC;
    }
}


