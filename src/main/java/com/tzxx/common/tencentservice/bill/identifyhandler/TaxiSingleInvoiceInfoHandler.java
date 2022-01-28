package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.TaxiInvoice;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class TaxiSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<TaxiInvoice>{

    public TaxiSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected TaxiInvoice doHandle(Map<String, String> nameValueMap) {
        TaxiInvoice taxiInvoice = new TaxiInvoice();
        taxiInvoice.setAmount(nameValueMap.get("金额"));
        taxiInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        taxiInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        taxiInvoice.setDate(nameValueMap.get("日期"));
        return taxiInvoice;
    }
}
