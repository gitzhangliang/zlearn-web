package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;
/**
 * @author tzxx
 */
@Getter
@Setter
public class DingTalkCodeCallBack {
	
    
	private String userid;

	private String deviceId;
    
	private Boolean is_sys;

	private Integer sys_level;

	private String errcode;
    
	private String errmsg;

	public DingTalkCodeCallBack() {
		// TODO Auto-generated constructor stub
	}
}
