package com.tzxx.common.tencentservice.bill.category;

import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**增值税发票（卷票 ）
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class VatRollInvoice extends AbstractBill implements VerifyAuthenticity  {
    /**
     * 品名
     */
    private String productName;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 发票消费类型
     */
    private String invoiceType;

    /**
     * 合计金额(大写)
     */
    private String totalPriceInWords;

    /**
     * 合计金额(小写)
     */
    private String totalPriceInFigures;

    /**
     * 开票日期
     */
    private String invoiceDate;

    /**
     * 是否有公司印章
     */
    private String whetherHaveCompanySeal;

    /**
     * 服务类型
     */
    private String serviceType;


    /**
     * 机打号码
     */
    private String machineNumber;

    /**
     * 校验码
     */
    private String checkCode;

    /**
     * 省
     */
    private String province;

    /**
     * 种类
     */
    private String category;

    /**
     * 购买方名称
     */
    private String purchaserName;

    /**
     * 购买方识别号
     */
    private String purchaserIdentificationNumber;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售方识别号
     */
    private String sellerIdentificationNumber;

    /**
     * 市
     */
    private String city;


    @Override
    public String additionalForVerify() {
        int cutOutDigits = 6;
        if (StringUtils.isNotBlank(this.checkCode) && this.checkCode.length() >= cutOutDigits) {
            int length = this.checkCode.length();
            return this.checkCode.substring(length-6,length);
        }else{
            return null;
        }

    }

    @Override
    public String billDateForVerify() {
        if (StringUtils.isNotBlank(this.invoiceDate)) {
            String date = this.invoiceDate;
            try {
                return DateUtil.getDateFormat(DateUtil.parse(date,DateUtil.SIMPLE_ZN_TIME_SPLIT_PATTERN),DateUtil.SIMPLE_TIME_SPLIT_PATTERN);
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
    }


    @Override
    public String invoiceCodeForVerify() {
        return getInvoiceCode();
    }

    @Override
    public String invoiceNoForVerify() {
        return getInvoiceNo();
    }

    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.totalPriceInFigures)) {
            return new BigDecimal(this.totalPriceInFigures);
        }else{
            return null;
        }
    }

    @Override
    public Date invoiceDate() {
        if (StringUtils.isNotBlank(this.getInvoiceDate())) {
            return DateUtil.parse(this.getInvoiceDate(),DateUtil.SIMPLE_ZN_TIME_SPLIT_PATTERN);
        }else{
            return null;
        }
    }

    @Override
    public String number() {
        return this.invoiceNo;
    }

    @Override
    public String code() {
        return this.invoiceCode;
    }

    @Override
    public String content() {
        return this.productName;
    }
}
