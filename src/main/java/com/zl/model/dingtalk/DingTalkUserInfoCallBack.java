package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author tzxx
 */
public class DingTalkUserInfoCallBack {
	
	@Getter
    @Setter
	private String errcode;
	
	@Getter
    @Setter
	private String errmsg;
	
	/**
	 * 员工唯一标识ID
	 */
	@Getter
    @Setter
	private String userid;
	/**
	 * 成员名称
	 */
	@Getter
    @Setter
	private String name;

	/**
	 * 分机号
	 */
	@Getter
    @Setter
	private String tel;

	/**
	 * 办公地点
	 */
	@Getter
    @Setter
	private String workPlace;
	/**
	 * 备注
	 */
	@Getter
    @Setter
	private String remark;
	/**
	 * 手机号码
	 */
	@Getter
    @Setter
	private String mobile;
	/**
	 * 员工的电子邮箱
	 */
	@Getter
    @Setter
	private String eamil;
	/**
	 * 员工的企业邮箱
	 */
	@Getter
    @Setter
	private String orgEmail;

	/**
	 * 是否已经激活, true表示已激活, false表示未激活
	 */
	@Getter
    @Setter
	private Boolean active;

	/**
	 * 在对应的部门中的排序, Map结构的json字符串, key是部门的Id, value是人员在这个部门的排序值
	 */
	@Getter
    @Setter
	private String orderInDepts;
	/**
	 * 是否为企业的管理员, true表示是, false表示不是
	 */
	@Getter
    @Setter
	private Boolean isAdmin;
	/**
	 * 是否为企业的老板, true表示是, false表示不是（不能通过接口设置,可以通过OA后台设置）
	 */
	@Getter
    @Setter
	private Boolean isBoss;
	/**
	 * 钉钉Id,在钉钉全局范围内标识用户的身份（不可修改）
	 */
	@Getter
    @Setter
	private String dingId;

	/**
	 * 在当前isv全局范围内唯一标识一个用户的身份,用户无法修改
	 */
	@Getter
    @Setter
	private String unionid;

	/**
	 * 	在对应的部门中是否为主管, Map结构的json字符串, key是部门的Id, value是人员在这个部门中是否为主管, true表示是, false表示不是
	 */
	@Getter
    @Setter
	private String isLeaderInDepts;
	/**
	 * 是否号码隐藏, true表示隐藏, false表示不隐藏
	 */
	@Getter
    @Setter
	private Boolean isHide;
	/**
	 * 成员所属部门id列表
	 */
	@Getter
    @Setter
	private Integer[] department;

	/**
	 * 职位信息
	 */
	@Getter
    @Setter
	private String position;

	/**
	 * 头像url
	 */
	@Getter
    @Setter
	private String avatar;
	/**
	 * 员工工号
	 */
	@Getter
    @Setter
	private String jobnumber; 
	/**
	 * 	扩展属性，可以设置多种属性(但手机上最多只能显示10个扩展属性，具体显示哪些属性，请到OA管理后台->设置->通讯录信息设置和OA管理后台->设置->手机端显示信息设置
	 */
	@Getter
    @Setter
	private Map<String, String> extattr;

	/**
	 * 角色信息（ISV不可见），json数组格式
	 */
	@Getter
    @Setter
	private List<DingTalkRole> roles;
}
