package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tzxx
 */
public class DingTalkSignature {
	/**
	 * 应用的标识
	 */
	@Getter
    @Setter
	private String agentId;
	/**
	 * 签名
	 */
	@Getter
    @Setter
	private String signature;
	/**
	 * 随机字符串
	 */
	@Getter
    @Setter
	private String nonceStr;
	/**
	 * 时间戳
	 */
	@Getter
    @Setter
	private String timeStamp;
	@Getter
    @Setter
	private String url;
	@Getter
    @Setter
	private String jsapi_ticket;
	@Getter
    @Setter
	private String corpId;

}
