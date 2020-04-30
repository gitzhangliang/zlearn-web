package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zl
 */
public class DingTalkTokenCallBack {
	@Getter
    @Setter
	private String access_token;
	@Getter
    @Setter
	private String errcode;
	@Getter
    @Setter
	private String errmsg;

	public DingTalkTokenCallBack() {
		// TODO Auto-generated constructor stub
	}	
}
