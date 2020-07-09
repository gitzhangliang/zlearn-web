package com.zl.spring.bean.autowiredandqualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
@Component
public class Customer {
    @Autowired
    @Qualifier("service1")
    private Service service1;

    @Autowired
    @Qualifier("service2")
    private Service service2;

//    public String serviceName(){
//        return service.service();
//    }

    public String serviceName1(){
        return service1.service();
    }

    public String serviceName2(){
        return service2.service();
    }
}
