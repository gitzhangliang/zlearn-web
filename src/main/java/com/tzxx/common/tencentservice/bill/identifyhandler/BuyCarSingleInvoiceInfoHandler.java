package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.BuyCarInvoice;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class BuyCarSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<BuyCarInvoice>{

    public BuyCarSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected BuyCarInvoice doHandle(Map<String, String> nameValueMap) {
        BuyCarInvoice buyCarInvoice = new BuyCarInvoice();
        buyCarInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        buyCarInvoice.setInvoiceDate(nameValueMap.get("开票日期"));
        buyCarInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        buyCarInvoice.setVatTaxRate(nameValueMap.get("增值税税率或征收率"));
        buyCarInvoice.setVatTaxAmount(nameValueMap.get("增值税税额"));
        buyCarInvoice.setPriceExcludingTaxInFigures(nameValueMap.get("不含税价(小写)"));
        buyCarInvoice.setTotalPriceAndTaxInFigures(nameValueMap.get("价税合计(小写)"));
        buyCarInvoice.setTotalPriceAndTax(nameValueMap.get("价税合计"));
        if (StringUtils.isBlank(buyCarInvoice.getTotalPriceAndTaxInFigures())) {
            buyCarInvoice.setTotalPriceAndTaxInFigures(nameValueMap.get("车价合计（小写）"));
        }
        return buyCarInvoice;
    }
}
