package com.tzxx.common.tencentservice.bill.model;

import com.tzxx.common.tencentservice.bill.enumerations.VerifyFailCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/2/1.
 */
@Data
public class VatInvoiceVerifyErrorRecord {
    private String invoiceCode;
    private String invoiceNo;
    private String invoiceDate;
    private String additional;
    private String imageUrl;
    private String imageBase64;
    private String errorMsg;
    private String errorCode;
    private String billId;

    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }

    public static VatInvoiceVerifyErrorRecord of(VatInvoiceVerify verify){
        VatInvoiceVerifyErrorRecord errorRecord = new VatInvoiceVerifyErrorRecord();
        errorRecord.setErrorMsg(VerifyFailCodeEnum.INVALID_PARAMETER_VALUE_INVALID_PARAMETER_VALUE_LIMIT.getMessage());
        errorRecord.setErrorCode(VerifyFailCodeEnum.INVALID_PARAMETER_VALUE_INVALID_PARAMETER_VALUE_LIMIT.getCode());
        errorRecord.setImageUrl(verify.getImageUrl());
        errorRecord.setImageBase64(verify.getImageBase64());
        errorRecord.setInvoiceCode(verify.getInvoiceCode());
        errorRecord.setInvoiceNo(verify.getInvoiceNo());
        errorRecord.setInvoiceDate(verify.getInvoiceDate());
        errorRecord.setAdditional(verify.getAdditional());
        errorRecord.setBillId(verify.getBillId());
        return errorRecord;
    }
}
