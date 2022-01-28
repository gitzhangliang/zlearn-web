package com.tzxx.common.tencentservice.bill.category;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**火车票
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class TrainBill extends AbstractBill  {
    /**
     * 出发时间
     */
    private String departureTime;


    /**
     * 出发站
     */
    private String departureStation;



    /**
     * 到达站
     */
    private String arrivalStation;


    /**
     * 发票消费类型
     */
    private String invoiceConsumptionType;


    /**
     * 席别
     */
    private String level;


    /**
     * 序列号
     */
    private String serialNumber;


    /**
     * 座位号
     */
    private String seatNumber;


    /**
     * 票价
     */
    private String ticketPrice;


    /**
     * 编号
     */
    private String number;


    /**
     * 车次
     */
    private String trainNumber;


    /**
     * 姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idCard;

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
        if (StringUtils.isNotBlank(this.departureTime)) {
            int index = this.departureTime.indexOf('日');
            String time = this.departureTime.substring(0, index + 1);
            return DateUtil.parse(time,DateUtil.SIMPLE_ZN_TIME_SPLIT_PATTERN);
        }else{
            return null;
        }

    }

    @Override
    public String number() {
        return this.number;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public String content() {
        return BillTypeEnum.TRAIN.getName();
    }
}
