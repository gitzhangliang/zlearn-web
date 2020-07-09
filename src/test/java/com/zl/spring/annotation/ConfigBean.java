package com.zl.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author tzxx
 * @date 2019/5/9.
 */
@Configuration
@Import({ConfigBeanC.class})
public class ConfigBean {
    @Bean
    public BeanB getBeanB(BeanD beanD){
        BeanB beanB = new BeanB();
        beanB.setBeanD(beanD);
        return beanB;
    }
    @Bean
    public BeanD getBeanD(){
        return new BeanD();
    }
}


