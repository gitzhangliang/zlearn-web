package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**定额发票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class QuotaInvoice extends AbstractBill {
    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 小写金额
     */
    private String amountInFigures;

    /**
     * 大写金额
     */
    private String amountInWords;


    /**
     * 省
     */
    private String province;


    /**
     * 市
     */
    private String city;

    /**
     * 发票消费类型
     */
    private String invoiceType;

    /**
     * 是否有公司印章
     */
    private String whetherHaveCompanySeal;

    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.amountInFigures)) {
            return new BigDecimal(this.amountInFigures);
        }else{
            return null;
        }
    }

    @Override
    public Date invoiceDate() {
        return null;
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
        return BillTypeEnum.QUOTA.getName();
    }

}
