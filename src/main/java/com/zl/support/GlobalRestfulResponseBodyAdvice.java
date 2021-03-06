package com.zl.support;

import com.zl.annotation.OriginalControllerReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * @author zl
 */
@ControllerAdvice(basePackages = "com.zl")
@Slf4j
public class GlobalRestfulResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        Annotation originalControllerReturnValue = methodParameter.getMethodAnnotation(OriginalControllerReturnValue.class);
        if (originalControllerReturnValue != null) {
            return obj;
        }
        JsonResult value;
        if (obj instanceof JsonResult) {
            value = ( JsonResult ) obj;
        } else {
            value = new JsonResult<>(obj);
        }
        return value;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

}
