package com.zl.job.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 执行定时任务
 * @author zl
 */
public class ScheduleRunnable {

    private Object target;
    private Method method;
    private String params;

    ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
        this.target = SpringContextUtil.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }


    public void run() throws InvocationTargetException, IllegalAccessException {
        ReflectionUtils.makeAccessible(method);
        if (StringUtils.isNotBlank(params)) {
            method.invoke(target, params);
        } else {
            method.invoke(target);
        }

    }

}
