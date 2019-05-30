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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		//declare pointcut
	}

	@Around("writeLog()")
	public Object writeLogAbountMethodInfo(ProceedingJoinPoint joinPoint) throws Throwable {
		Thread current = Thread.currentThread();
		Object object = null;
		if(logger.isInfoEnabled()){
			StringBuilder builder = new StringBuilder();
			builder.append(current.getId()).append("-->").append(joinPoint.getTarget());
			logger.info(builder.append("-->methodName:{}").toString(), joinPoint.getSignature().getName());
			builder.append("-->").append(joinPoint.getSignature().getName());
			List<String> params = new ArrayList<>();
			if(joinPoint.getArgs().length>0){
				for (Object obj : joinPoint.getArgs()) {
					if(obj instanceof HttpServletRequest || obj instanceof HttpServletResponse){
						continue;
					}
					params.add(JsonUtil.objToStr(obj));
				}
			}
			logger.info(builder.append("-->allParameters:{}").toString(),  StringUtils.join(params.toArray()," ; "));
			Long startTime = System.currentTimeMillis();
			object = joinPoint.proceed();
			Long endTime = System.currentTimeMillis();
			logger.info(builder.append("-->timeLength:{}").toString(), endTime-startTime);
			logger.info(builder.append("-->returnValue:{}").toString(), object);
		}
		return object;

	}
}