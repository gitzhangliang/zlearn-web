package com.zl.easyexcel.annotation;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.*;

/**
 * @author zhangliang
 * @date 2019/12/19.
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelStyle {
    String fontName() default "微软雅黑";
    short fontHeightInPoints() default 12;
    HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;
    VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;
}
