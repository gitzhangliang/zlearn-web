//package com.tzxx.common.aspect;
//
//import com.tzxx.common.annotation.NoRepeatSubmit;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.UUID;
//
///**
// * @author zhangliang
// * @date 2020/3/26.
// */
//@Slf4j
//@Aspect
//@Component
//public class NoRepeatSubmitAspect {
//
//
//    @Pointcut("@annotation(com.tzxx.common.annotation.NoRepeatSubmit)")
//    public void pointcut() {
//        // do nothing
//    }
//
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        NoRepeatSubmit noRepeatSubmit = method.getAnnotation(NoRepeatSubmit.class);
//        long lockTime = noRepeatSubmit.lockTime();
//        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
//        String token = request.getHeader("auth");
//        String key = token + "_" + request.getServletPath();
//        String clientId = getClientId();
//        if (redisService.tryLock(key,clientId,lockTime)) {
//            try {
//                return pjp.proceed();
//            } catch (Throwable e) {
//                log.error("提交系统出错:{0}",e);
//            }
//        } else {
//            log.info("tryLock fail key = [{}]",key);
//        }
//    }
//
//    private String getClientId(){
//        return UUID.randomUUID().toString();
//    }
//}
