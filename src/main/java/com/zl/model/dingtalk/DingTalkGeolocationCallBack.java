package com.zl.model.dingtalk;

import lombok.Getter;
import lombok.Setter;
/**
 * @author zl
 */
public class DingTalkGeolocationCallBack {	
	/**
	 * 错误码
	 */
	@Getter
    @Setter
	private String errorCode;
	/**
	 * 对错误码的描述
	 */
	@Getter
    @Setter
	private String errorMessage;
	/**
	 * 经度
	 */
	@Getter
    @Setter
	private Double longitude;	
	/**
	 * 纬度
	 */
	@Getter
    @Setter
	private Double latitude;	
	/**
	 * 实际的定位精度半径（单位米）
	 */
	@Getter
    @Setter
	private Integer accuracy;	
	/**
	 * 	格式化地址，如：北京市朝阳区南磨房镇北京国家广告产业园区
	 */
	@Getter
    @Setter
	private String address;
	/**
	 * 省份，如：北京市
	 */
	@Getter
    @Setter
	private String province;	
	/**
	 * 城市，直辖市会返回空
	 */
	@Getter
    @Setter
	private String city;	
	/**
	 * 行政区，如：朝阳区
	 */
	@Getter
    @Setter
	private String district;	
	/**
	 * 街道，如：西大望路甲12-2号楼
	 */
	@Getter
    @Setter
	private String road;	
	/**
	 * 当前设备网络类型，如：wifi、3g等
	 */
	@Getter
    @Setter
	private String netType;	
	/**
	 * 当前设备使用移动运营商，如：CMCC等
	 */
	@Getter
    @Setter
	private String operatorType;		
	/**
	 * 仅Android支持，wifi设置是否开启，不保证已连接上
	 */
	@Getter
    @Setter
	private Boolean isWifiEnabled;	
	/**
	 * 仅Android支持，gps设置是否开启，不保证已经连接上
	 */
	@Getter
    @Setter
	private Boolean isGpsEnabled;	
	/**
	 * 仅Android支持，定位返回的经纬度是否是模拟的结果
	 */
	@Getter
    @Setter
	private Boolean isFromMock;	
	/**
	 * 仅Android支持，我们使用的是混合定位，具体定位提供者有wifi/lbs/gps" 这三种
	 */
	@Getter
    @Setter
	private String provider;
	/**
	 * 仅Android支持，移动网络是设置是否开启，不保证已经连接上
	 */
	@Getter
    @Setter
	private Boolean isMobileEnabled;
	/**
	 * 时间
	 */
	@Getter
    @Setter
	private Long time;
	/**
	 * 国际移动电话设备识别码（International Mobile Equipment Identity）
	 */
	@Getter
    @Setter
	private String imei;

	@Getter
    @Setter
	private Integer locationType;

	public DingTalkGeolocationCallBack() {
	}
}
