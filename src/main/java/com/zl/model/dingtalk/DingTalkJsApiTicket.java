package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 */
@Getter
@Setter
public class DingTalkJsApiTicket {

	private String errcode;

	private String errmsg;

	private String ticket;

	private long expires_in;

}
