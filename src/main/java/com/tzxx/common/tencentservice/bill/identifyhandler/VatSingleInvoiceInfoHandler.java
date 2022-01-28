package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.VatInvoice;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class VatSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<VatInvoice>{

    public VatSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected VatInvoice doHandle(Map<String, String> nameValueMap) {
        VatInvoice vatInvoice = new VatInvoice();
        vatInvoice.setInvoiceNo(nameValueMap.get("发票号码"));
        vatInvoice.setInvoiceDate(nameValueMap.get("开票日期"));
        vatInvoice.setInvoiceCode(nameValueMap.get("发票代码"));
        vatInvoice.setCheckCode(nameValueMap.get("校验码"));
        vatInvoice.setAmount(nameValueMap.get("金额"));
        vatInvoice.setTaxRate(nameValueMap.get("税率"));
        vatInvoice.setTaxAmount(nameValueMap.get("税额"));
        vatInvoice.setAmountInFigures(nameValueMap.get("小写金额"));
        vatInvoice.setInvoiceName(nameValueMap.get("发票名称"));
        vatInvoice.setPurchaserName(nameValueMap.get("购买方名称"));
        if(StringUtils.isNotBlank(vatInvoice.getInvoiceName())){
            vatInvoice.setSpecial(vatInvoice.getInvoiceName().contains("专用"));
        }else{
            vatInvoice.setSpecial(false);
        }
        return vatInvoice;
    }
}
