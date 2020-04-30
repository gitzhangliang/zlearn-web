package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zl
 */
public class DingTalkMessageCallBack {
	/**
	 * 返回码
	 */
	@Getter
    @Setter
	private String errcode;	
	/**
	 * 对返回码的文本描述内容
	 */
	@Getter
    @Setter
	private String errmsg;	
	/**
	 * 无效的userid
	 */
	@Getter
    @Setter
	private String invaliduser;	
	/**
	 * 无效的部门id
	 */
	@Getter
    @Setter
	private String invalidparty;	
	/**
	 * 因发送消息过于频繁或超量而被流控过滤后实际未发送的userid。未被限流的接收者仍会被成功发送。限流规则包括：1、给同一用户发相同内容消息一天仅允许一次；2、如果是ISV接入方式，给同一用户发消息一天不得超过50次；如果是企业接入方式，此上限为500。
	 */
	@Getter
    @Setter
	private String forbiddenUserId;	
	/**
	 * 标识企业消息的id，字符串，最长128个字符
	 */
	@Getter
    @Setter
	private String messageId;
}
