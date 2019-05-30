package com.zl.exception;


/**
 * @author tzxx
 */
public class CoderException extends BasicException {

    public CoderException(int code, String message) {
        super(code,message);
    }
    public CoderException(String message) {
        super(message);
    }
}
