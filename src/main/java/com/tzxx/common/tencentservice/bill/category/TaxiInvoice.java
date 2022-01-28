package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**出租车发票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class TaxiInvoice extends AbstractBill {
    /**
     * 上车
     */
    private String getOnTime;

    /**
     * 下车
     */
    private String getOffTime;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 发票所在地
     */
    private String placeOfInvoice;

    /**
     * 发票消费类型
     */
    private String invoiceType;

    /**
     * 日期
     */
    private String date;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 车牌号
     */
    private String licensePlateNumber;


    /**
     * 金额
     */
    private String amount;


    /**
     * 里程
     */
    private String distance;


    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.amount)) {
            return new BigDecimal(this.amount);
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
        return BillTypeEnum.TAXI.getName();
    }
}
