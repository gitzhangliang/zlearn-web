package com.zl.spring.bean.autowiredandqualifier;

import org.springframework.stereotype.Component;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
@Component
public class Service2 implements Service {
    @Override
    public String service() {
        return "service2";
    }
}
