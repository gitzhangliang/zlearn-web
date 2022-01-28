package com.tzxx.common.tencentservice.bill;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
@Data
public class TencentOcrLog  {

    /**
     * 唯一标识 （一次调用多张图片token一致）
     */
    private String token;

    /**
     * 调用接口用户id
     */
    private Long userId;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 调用接口名称
     */
    private String interfaceName;

    /**
     * 接口成功响应（不触发异常）
     */
    private String resp;

    /**
     * 接口调用是否成功
     */
    private Boolean success;

    /**
     * 接口异常信息（触发异常）
     */
    private String errorMsg;

    /**
     * 错误码
     */
    private String code;
}
