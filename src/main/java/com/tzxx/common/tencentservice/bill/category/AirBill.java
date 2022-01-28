package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**机票行程单
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class AirBill extends AbstractBill {
    /**
     * 合计金额
     */
    private String totalAmount;
    /**
     * 燃油附加费
     */
    private String fuelSurcharge;
    /**
     * 票价
     */
    private String ticketPrice;

    /**
     * 印刷序号
     */
    private String printTheSerialNumber;

    /**
     * 填开日期
     */
    private String openingDate;


    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.totalAmount)) {
            return new BigDecimal(this.totalAmount);
        }else{
            return null;
        }
    }

    @Override
    public Date invoiceDate() {
        if (StringUtils.isNotBlank(this.openingDate)) {
            return DateUtil.parse(this.openingDate,DateUtil.SIMPLE_ZN_TIME_SPLIT_PATTERN);
        }else{
            return null;
        }
    }

    @Override
    public String number() {
        return this.printTheSerialNumber;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public String content() {
        return BillTypeEnum.AIR.getName();
    }
}
