package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.QuotaInvoice;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class QuotaSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<QuotaInvoice>{

    public QuotaSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected QuotaInvoice doHandle(Map<String, String> nameValueMap) {
        QuotaInvoice quotaInvoice = new QuotaInvoice();
        quotaInvoice.setAmountInFigures(nameValueMap.get("小写金额"));
        quotaInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        quotaInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        return quotaInvoice;
    }
}
