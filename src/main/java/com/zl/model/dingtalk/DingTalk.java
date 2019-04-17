package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class DingTalk {

	private String access_token;

	private String corpId;

	private String corpSecret;

	private String code;
	public DingTalk() {
		// TODO Auto-generated constructor stub
	}
	public DingTalk(String access_token, String corpId, String corpSecret) {
		super();
		this.access_token = access_token;
		this.corpId = corpId;
		this.corpSecret = corpSecret;
	}
}
