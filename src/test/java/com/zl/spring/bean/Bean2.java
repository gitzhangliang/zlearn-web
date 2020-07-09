package com.zl.spring.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 * @date 2019/4/15.
 */
@Setter
@Getter
public class Bean2 {
    public Bean2() {
    }

    public Bean2(Bean1 b1) {
        this.b1 = b1;
    }

    private Bean1 b1;
    public boolean register(){
        return b1.register();
    }
}
