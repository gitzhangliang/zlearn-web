package com.zl.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tzxx
 */
@ControllerAdvice(basePackages = "com.zl")
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult<Object> jsonErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
        JsonResult<Object> value =  new JsonResult<>(-1, ex.getMessage());
        logger.error("global_exception_handler_print_error", ex);
        return value;
    }

}