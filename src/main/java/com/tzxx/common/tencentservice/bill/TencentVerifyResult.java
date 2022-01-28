package com.tzxx.common.tencentservice.bill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangliang
 * @date 2021/2/4.
 */
@Data
public class TencentVerifyResult {
    private boolean success;
    private String errorMsg;
    private String customMsg;

    /**
     * 含税金额
     */
    private BigDecimal amount;
    /**
     * 不含税金额
     */
    private BigDecimal noTaxAmount;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 校验码
     */
    private String checkCode;

    private Long type;
}
