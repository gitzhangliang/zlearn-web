package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**购车发票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class BuyCarInvoice extends AbstractBill {
    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 开票日期
     */
    private String invoiceDate;

    /**
     * 不含税价(小写)
     */
    private String priceExcludingTaxInFigures;
    /**
     * 价税合计
     */
    private String totalPriceAndTax;
    /**
     * 价税合计(小写)
     */
    private String totalPriceAndTaxInFigures;
    /**
     * 增值税税率或征收率
     */
    private String vatTaxRate;
    /**
     * 增值税税额
     */
    private String vatTaxAmount;


    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.getTotalPriceAndTaxInFigures())) {
            String money = this.getTotalPriceAndTaxInFigures();
            money = money.replace("¥","");
            return new BigDecimal(money);
        }else{
            return null;
        }
    }

    @Override
    public Date invoiceDate() {
        if (StringUtils.isNotBlank(this.invoiceDate)) {
            return DateUtil.parse(this.invoiceDate,DateUtil.SIMPLE_TIME_SPLIT_PATTERN);
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
        return BillTypeEnum.BUY_CAR.getName();
    }
}
