package com.zl.config.aop;

import com.zl.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzxx
 */
@Aspect
@Component
public class WebAspect {
	private Logger logger = LoggerFactory.getLogger(WebAspect.class);

	@Pointcut("execution (* com.zl.controller..*.*(..))")

	private void writeLog() {
	}

	@Around("writeLog()")
	public Object writeLogAbountMethodInfo(ProceedingJoinPoint joinPoint) throws Throwable {
		Thread current = Thread.currentThread();
		Object object = null;
		if(logger.isInfoEnabled()){
			StringBuffer buffer = new StringBuffer();
			buffer.append(current.getId()+"-->"+joinPoint.getTarget());
			logger.info(buffer.toString()+"-->methodName:{}", joinPoint.getSignature().getName());
			buffer.append("-->"+joinPoint.getSignature().getName());
			List<String> params = new ArrayList<>();
			if(joinPoint.getArgs().length>0){
				for (Object obj : joinPoint.getArgs()) {
					params.add(JsonUtil.objToStr(obj));
				}
			}
			logger.info(buffer.toString()+"-->allParameters:{}",  StringUtils.join(params.toArray()," ; "));
			Long startTime = System.currentTimeMillis();
			object = joinPoint.proceed();
			Long endTime = System.currentTimeMillis();
			logger.info(buffer+"-->timeLength:{}", endTime-startTime);
			logger.info(buffer+"-->returnValue:{}", object);
		}
		return object;

	}
}