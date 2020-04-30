package com.zl.support;

import com.zl.exception.CoderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zl
 */
@ControllerAdvice(basePackages = "com.zl")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult<Object> jsonErrorHandler(Exception ex) {
        JsonResult<Object> value =  new JsonResult<>(-1, ex.getMessage());
        log.error("global_exception_handler_print_error:{0}", ex);
        return value;
    }

    @ExceptionHandler(value = CoderException.class)
    @ResponseBody
    public JsonResult<Object> jsonCoderExceptionHandler(CoderException ex) {
        JsonResult<Object> value =  new JsonResult<>(ex.getCode(), ex.getMessage());
        log.error("coderException:{0}", ex);
        return value;
    }

}