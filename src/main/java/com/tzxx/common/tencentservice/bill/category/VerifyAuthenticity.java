package com.tzxx.common.tencentservice.bill.category;

/**
 * @author zhangliang
 * @date 2021/2/20.
 */
public interface VerifyAuthenticity {
    String additionalForVerify();
    String billDateForVerify();
    String invoiceCodeForVerify();
    String invoiceNoForVerify();
}
