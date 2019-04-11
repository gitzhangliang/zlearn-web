package com.zl.support;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 */
@Getter@Setter
public class JsonResult<T> {
    private int status = 0;
    private T data;
    private String errMsg;

    public JsonResult() {
    }

    public JsonResult(T data) {
        this.data = data;
    }

    public JsonResult(int status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public JsonResult(int status, T data, String errMsg) {
        this.status = status;
        this.data = data;
        this.errMsg = errMsg;
    }
}
