package com.tzxx.common.tencentservice.bill.model;

import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceVerifyResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/2/1.
 */
@Data
public class VatInvoiceVerifySuccessRecord {
    private String invoiceCode;
    private String invoiceNo;
    private String invoiceDate;
    private String additional;
    private String imageUrl;
    private String imageBase64;
    private String billId;
    private VatInvoiceVerifyResponse response;

    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
