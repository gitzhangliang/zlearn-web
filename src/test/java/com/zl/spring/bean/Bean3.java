package com.zl.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
@Component
public class Bean3 {

    public String name;
    @Autowired
    private Bean1 b1;
    public boolean register(){
        return b1.register();
    }
}
