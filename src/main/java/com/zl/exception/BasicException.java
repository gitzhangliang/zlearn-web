package com.zl.exception;

import lombok.Getter;

/**
 * @author zl
 */
@Getter
public class BasicException extends RuntimeException {
	private final int code;

	public BasicException(String message) {
		super(message);
		this.code = -1;
	}
}
