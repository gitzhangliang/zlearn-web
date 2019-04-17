package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DingTalkRequest {
	private String touser;
	private String toparty;
	private String agentid;
	private String msgtype;
	private DingTalkText text;

	public DingTalkRequest() {
		// TODO Auto-generated constructor stub
	}



	public DingTalkRequest(String touser, String agentid, String msgtype, DingTalkText text) {
		super();
		this.touser = touser;
		this.agentid = agentid;
		this.msgtype = msgtype;
		this.text = text;
	}

	public DingTalkRequest(String touser, String toparty, String agentid, String msgtype, DingTalkText text) {
		super();
		this.touser = touser;
		this.toparty = toparty;
		this.agentid = agentid;
		this.msgtype = msgtype;
		this.text = text;
	}

}
