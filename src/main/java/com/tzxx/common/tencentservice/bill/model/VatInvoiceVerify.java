package com.tzxx.common.tencentservice.bill.model;

import com.tzxx.common.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author zhangliang
 * @date 2021/2/1.
 */
@Data
public class VatInvoiceVerify {
    private String invoiceCode;
    private String invoiceNo;
    private String invoiceDate;
    private String additional;
    private String imageUrl;
    private String imageBase64;
    private String billId;
    public VatInvoiceVerify(){}

    public VatInvoiceVerify(String invoiceCode, String invoiceNo, String invoiceDate, String additional) {
        this.invoiceCode = invoiceCode;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.additional = additional;
    }
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }

    public boolean notBlankParamForVerify(){
        return StringUtils.isNotBlank(invoiceCode) && StringUtils.isNotBlank(invoiceNo) && StringUtils.isNotBlank(invoiceDate) && StringUtils.isNotBlank(additional);
    }

    public boolean isTodayInvoice() {
        Date today = DateUtil.now(DateUtil.SIMPLE_TIME_SPLIT_PATTERN);
        Date date = DateUtil.parse(this.invoiceDate,DateUtil.SIMPLE_TIME_SPLIT_PATTERN);
        return today.compareTo(date) == 0;
    }
}
