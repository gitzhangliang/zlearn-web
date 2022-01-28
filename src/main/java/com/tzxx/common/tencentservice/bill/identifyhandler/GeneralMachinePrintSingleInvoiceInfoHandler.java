package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.GeneralMachinePrintInvoice;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class GeneralMachinePrintSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<GeneralMachinePrintInvoice>{

    public GeneralMachinePrintSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected GeneralMachinePrintInvoice doHandle(Map<String, String> nameValueMap) {
        GeneralMachinePrintInvoice generalMachinePrintInvoice = new GeneralMachinePrintInvoice();
        generalMachinePrintInvoice.setTotalPriceInFigures(nameValueMap.get("合计金额(小写)"));
        generalMachinePrintInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        generalMachinePrintInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        generalMachinePrintInvoice.setDate(nameValueMap.get("日期"));
        return generalMachinePrintInvoice;
    }
}
