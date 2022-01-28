package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**通用机打发票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class GeneralMachinePrintInvoice extends AbstractBill {
    /**
     * 合计金额(小写)
     */
    private String totalPriceInFigures;
    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;
    /**
     * 日期
     */
    private String date;

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
        if (StringUtils.isNotBlank(this.date)) {
            return DateUtil.parse(this.date,DateUtil.SIMPLE_ZN_TIME_SPLIT_PATTERN);
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
        return BillTypeEnum.GENERAL_MACHINE_PRINT.getName();
    }
}
