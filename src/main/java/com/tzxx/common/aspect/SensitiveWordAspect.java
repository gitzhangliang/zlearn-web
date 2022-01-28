package com.tzxx.common.aspect;

import com.tzxx.common.annotation.SensitiveWordMark;

import com.zl.utils.SensitiveWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangliang
 * @date 2020/3/26.
 */
@Slf4j
@Aspect
@Component
public class SensitiveWordAspect {




    @Pointcut("@annotation(com.tzxx.common.annotation.SensitiveWordValid)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException  {
        Object arg = pjp.getArgs()[0];
        //预提交 校验表单敏感词并记录,..
        Set<String> sensitiveWords = new HashSet<>(new ArrayList<>());
        List<String> errorList = new ArrayList<>();
        validate(arg.getClass(),arg,sensitiveWords,errorList);
        if (!errorList.isEmpty()) {
            //error
        }
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private void validate(Class<?> clazz,Object arg,Set<String> sensitiveWords,List<String> errorList)  throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(SensitiveWordMark.class)) {
                Class<?> type = declaredField.getType();
                boolean beanCustom = type.getPackage().getName().contains("com.tzxx");
                if (beanCustom) {
                    Object fieldValue = getFieldValueByReflect(declaredField,arg);
                    validate(type,fieldValue,sensitiveWords,errorList);

                }else if (type.isAssignableFrom(String.class)) {
                    SensitiveWordMark mark = declaredField.getDeclaredAnnotation(SensitiveWordMark.class);
                    stringFieldFilter(mark,declaredField,arg,sensitiveWords,errorList);
                }
            }
        }
    }

    private void stringFieldFilter(SensitiveWordMark mark,Field declaredField,Object arg,Set<String> sensitiveWords,List<String> errorList) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldValue = (String)getFieldValueByReflect(declaredField,arg);
        if(StringUtils.isBlank(fieldValue)){
            return;
        }
        Set<String> sensitiveWordSet = new SensitiveWordUtil(sensitiveWords).getSensitiveWord(fieldValue.replaceAll("\\r*\\n*", ""));
        if (!sensitiveWordSet.isEmpty()) {
            errorList.add("【"+mark.value()+"】中包含敏感词："+String.join("、",sensitiveWordSet));
        }
    }
    private static String firstLetterToUpper(String str){
        char[] array = str.toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }

    private Object getFieldValueByReflect(Field field,Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = field.getName();
        String getMethodName =  "get"+firstLetterToUpper(name);
        Method method = object.getClass().getMethod(getMethodName);
        return method.invoke(object);
    }
}
