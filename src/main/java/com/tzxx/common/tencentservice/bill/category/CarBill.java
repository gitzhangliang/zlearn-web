package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.tzxx.common.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**汽车票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class CarBill extends AbstractBill {
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
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 票价
     */
    private String ticketPrice;

    /**
     * 姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 始发地
     */
    private String placeOfOrigin;

    /**
     * 目的地
     */
    private String destination;

    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.ticketPrice)) {
            return new BigDecimal(this.ticketPrice);
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
        return BillTypeEnum.CAR.getName();
    }
}
