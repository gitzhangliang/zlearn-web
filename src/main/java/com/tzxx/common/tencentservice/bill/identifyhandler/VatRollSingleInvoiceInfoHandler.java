package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.VatRollInvoice;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class VatRollSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<VatRollInvoice>{

    public VatRollSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected VatRollInvoice doHandle(Map<String, String> nameValueMap) {
        VatRollInvoice vatRollInvoice = new VatRollInvoice();
        vatRollInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        vatRollInvoice.setInvoiceDate(nameValueMap.get("开票日期"));
        vatRollInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        vatRollInvoice.setTotalPriceInFigures(nameValueMap.get("合计金额(小写)"));
        vatRollInvoice.setCheckCode(nameValueMap.get("校验码"));
        return vatRollInvoice;
    }
}
