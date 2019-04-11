package com.zl.annotation;

import java.lang.annotation.*;

/**
 *不通过统一的响应处理，可以使用此注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OriginalControllerReturnValue {
}
