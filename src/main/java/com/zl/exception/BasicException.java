package com.zl.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 */
@Getter
public class BasicException extends RuntimeException {
	private final int code;

	public BasicException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BasicException(String message) {
		super(message);
		this.code = -1;
	}
}
