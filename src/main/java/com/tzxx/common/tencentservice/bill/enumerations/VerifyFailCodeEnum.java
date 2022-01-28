package com.tzxx.common.tencentservice.bill.enumerations;

import lombok.Getter;

/**
 * @author zhangliang
 * @date 2021/2/4.
 */
@Getter
public enum VerifyFailCodeEnum {
    //校验失败枚举类
    FAILED_OPERATION_ARREARS_ERROR("FailedOperation.ArrearsError","帐号已欠费","帐号已欠费"),
    FAILED_OPERATION_COUNT_LIMIT_ERROR("FailedOperation.CountLimitError","今日次数达到限制","今日次数达到限制"),
    FAILED_OPERATION_INVOICE_MISMATCH("FailedOperation.InvoiceMismatch","发票数据不一致","发票核验失败"),
    FAILED_OPERATION_UN_KNOW_ERROR("FailedOperation.UnKnowError","未知错误","发票核验失败"),
    FAILED_OPERATION_UN_OPEN_ERROR("FailedOperation.UnOpenError","服务未开通","服务未开通"),
    INVALID_PARAMETER_VALUE_INVALID_PARAMETER_VALUE_LIMIT("InvalidParameterValue.InvalidParameterValueLimit","参数值错误","发票核验失败"),
    RESOURCE_NOT_FOUND_NO_INVOICE("ResourceNotFound.NoInvoice","发票不存在","发票核验失败");

    private final String code;
    private final String message;
    private final String customMessage;

    VerifyFailCodeEnum(String code,String message,String customMessage){
        this.code = code;
        this.message = message;
        this.customMessage = customMessage;
    }

    public static String getCustomMessageByCode(String code){
        for(VerifyFailCodeEnum e:VerifyFailCodeEnum.values()){
            if(code.equals(e.code)){
                return e.customMessage;
            }
        }
        return null;
    }
}
