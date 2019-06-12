package com.zl.model.dingtalk;

import com.alibaba.fastjson.JSON;
import com.zl.support.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author tzxx
 */
@Component
public class DingTalkComponent {
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public static String accessToken = null; 
	public static String jsApiTicket = null;

	public static final String SUCCESS_CODE = "0";
	//钉钉
	public static final String CORPID = "";
	public static final String CORPSECRET = "";
	public static final String AGENTID = "";
	
	public void dingTalkSetting(){
		setAccessToken();
		setJsApiTicket();
	}
		
	public void setAccessToken() {
		accessToken = getAccessToken();	
	}

	public void setJsApiTicket() {
		jsApiTicket = getJsApiTicket();		
	}
	
	public String getAccessToken() {
		String url ="https://oapi.dingtalk.com/gettoken?corpid={corpid}&corpsecret={corpsecret}";
		Map<String, Object> uriVariables = new HashMap<String, Object>();
		uriVariables.put("corpid", CORPID);
		uriVariables.put("corpsecret", CORPSECRET);
		DingTalkTokenCallBack result = restTemplate.getForObject(url, DingTalkTokenCallBack.class, uriVariables);
		if(!SUCCESS_CODE.equals(result.getErrcode())){
			logger.info("getAccessToken dingTalkMessage:{}" , result.getErrmsg());
		}
		return result.getAccess_token();
	}
	
	public String getJsApiTicket() {
		String accessToken = DingTalkComponent.accessToken;
		String url ="https://oapi.dingtalk.com/get_jsapi_ticket?access_token={accessToken}&type=jsapi";
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put("accessToken", accessToken);
		DingTalkJsApiTicket result = restTemplate.getForObject(url, DingTalkJsApiTicket.class, uriVariables);
		if(!SUCCESS_CODE.equals(result.getErrcode())){
			logger.info("getJsApiTicket dingTalkMessage:{}",  result.getErrmsg());
		}
		return result.getTicket();
	}

	public DingTalkRequest getSendRequest(List<String> userIds,String content) {
		DingTalkText dtt = new DingTalkText();
		dtt.setContent(content);
		DingTalkRequest dtr = new DingTalkRequest();
		StringBuffer touser = new StringBuffer();
		for (String userId : userIds) {
			touser.append(userId+"|");
		}
		dtr.setTouser(touser.toString().substring(0, touser.toString().lastIndexOf("|")));
		dtr.setAgentid(AGENTID);
		dtr.setMsgtype("text");
		dtr.setText(dtt);
		return dtr;
	}

	public DingTalkCodeCallBack getUserInfoByCode(String code) {
		String accessToken = DingTalkComponent.accessToken;
		String url ="https://oapi.dingtalk.com/user/getuserinfo?access_token={accessToken}&code={code}";
		Map<String, Object> uriVariables = new HashMap<>(16);
		uriVariables.put("accessToken", accessToken);
		uriVariables.put("code", code);
		DingTalkCodeCallBack result = restTemplate.getForObject(url, DingTalkCodeCallBack.class, uriVariables);
		if(!SUCCESS_CODE.equals(result.getErrcode())){
			logger.info("getUserInfoByCode dingTalkMessage:" + result.getErrmsg());
		}
		return result;
	}
	@Async
	public void sendDingTalk(String content,List<String> userIds){
		if(userIds != null && userIds.size()>0){
			String accessToken = DingTalkComponent.accessToken;
			String url = "https://oapi.dingtalk.com/message/send?access_token={accessToken}";
			Map<String, Object> uriVariables = new HashMap<String, Object>();
			uriVariables.put("accessToken", accessToken);
			DingTalkMessageCallBack callBack = restTemplate.postForObject(url, getSendRequest(userIds,content), DingTalkMessageCallBack.class, uriVariables);
			if(!SUCCESS_CODE.equals(callBack.getErrcode())){
				logger.info("sendDingTalk dingTalkMessage:" + callBack.getErrmsg());
			}
		}
	}
	
	@Async
	public void sendDingTalk(String content,String userId){
		List<String> userIds = new ArrayList<>();
		userIds.add(userId);
		sendDingTalk(content,userIds);
	}
	
	public DingTalkSignature getSignature(String url) {
		logger.info("when getSignature  jsApiTicket:" +jsApiTicket);
		String jsapi_ticket = jsApiTicket;
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		DingTalkSignature ds = new DingTalkSignature();
		ds.setAgentId(AGENTID);
		ds.setNonceStr(nonce_str);
		ds.setSignature(signature(jsapi_ticket,nonce_str,timestamp,url));
		ds.setTimeStamp(timestamp);
		ds.setUrl(url);
		ds.setJsapi_ticket(jsapi_ticket);
		ds.setCorpId(CORPID);
		return ds;
	}

	
	private String signature(String ticket, String nonceStr, String timeStamp, String url){
		logger.info("url:" + url);
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp
				+ "&url=" + url;
		String signature = "";
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			signature =  byteToHex(sha1.digest());
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return signature;
	}
	
	private String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**生成随机字符串
	 * @return
	 */
	private String create_nonce_str() {
		return "abcdefg";
	}

	/**生成时间戳字符串
	 * @return
	 */
	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public DingTalkUserInfoCallBack getUserInfoByUserId(String userId) {
		String accessToken = DingTalkComponent.accessToken;
		String url ="https://oapi.dingtalk.com/user/get?access_token={accessToken}&userid={userId}";
		Map<String, Object> uriVariables = new HashMap<>(16);
		uriVariables.put("accessToken", accessToken);
		uriVariables.put("userId", userId);
		logger.info("request getUserInfoByUserId userId:" + userId);
		DingTalkUserInfoCallBack result = restTemplate.getForObject(url, DingTalkUserInfoCallBack.class, uriVariables);
		logger.info("response getUserInfoByUserId    Message:" + JSON.toJSONString(result));
		if(!SUCCESS_CODE.equals(result.getErrcode())){
			logger.info("dingTalkMessage:" + result.getErrmsg());
		}
		return result;
	}
	
	public DingTalkDepartmentCallBack getDepartmentInfoByDepartmentId(Integer departmentId) {
		String accessToken = DingTalkComponent.accessToken;
		String url ="https://oapi.dingtalk.com/department/get?access_token={accessToken}&id={departmentId}";
		Map<String, Object> uriVariables = new HashMap<>(16);
		uriVariables.put("accessToken", accessToken);
		uriVariables.put("departmentId", departmentId);
		logger.info("request departmentId:" + departmentId);
		DingTalkDepartmentCallBack result = restTemplate.getForObject(url, DingTalkDepartmentCallBack.class, uriVariables);
		logger.info("response result:" +  JSON.toJSONString(result));
		if(!SUCCESS_CODE.equals(result.getErrcode())){
			logger.info("dingTalkMessage:" + result.getErrmsg());
		}
		return result;
	}
}
