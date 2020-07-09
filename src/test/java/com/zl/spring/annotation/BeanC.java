package com.zl.spring.annotation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 * @date 2019/5/9.
 */
@Setter
@Getter
public class BeanC {
    private BeanB beanB;
    private BeanA beanA;
}
