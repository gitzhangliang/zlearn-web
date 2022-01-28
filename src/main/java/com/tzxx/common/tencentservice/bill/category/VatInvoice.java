package com.tzxx.common.tencentservice.bill.category;

import com.zl.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**增值税发票
 * @author zhangliang
 * @date 2021/1/28.
 */
@Data
public class VatInvoice extends AbstractBill implements VerifyAuthenticity {
    /**
     * 货物或应税劳务、服务名称
     */
    private String billContent;

    /**
     * 金额
     */
    private String amount;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 税额
     */
    private String taxAmount;

    /**
     * 价税合计(大写)
     */
    private String totalPriceTaxInWords;

    /**
     * 发票代码
     */
    private String invoiceCode;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 发票名称
     */
    private String invoiceName;

    /**
     * 发票消费类型
     */
    private String invoiceType;

    /**
     * 合计税额
     */
    private String totalTaxAmount;

    /**
     * 合计金额
     */
    private String totalAmount;

    /**
     * 复核
     */
    private String reviewer;

    /**
     * 小写金额
     */
    private String amountInFigures;

    /**
     * 开票人
     */
    private String drawer;

    /**
     * 开票日期
     */
    private String invoiceDate;

    /**
     * 打印发票代码
     */
    private String printInvoiceCode;

    /**
     * 打印发票号码
     */
    private String printInvoiceNo;

    /**
     * 是否有公司印章
     */
    private String whetherHaveCompanySeal;

    /**
     * 校验码
     */
    private String checkCode;

    /**
     * 省
     */
    private String province;

    /**
     * 联次
     */
    private String joint;

    /**
     * 购买方名称
     */
    private String purchaserName;

    /**
     * 购买方识别号
     */
    private String purchaserIdentificationNumber;

    /**
     * 购买方地址、电话
     */
    private String purchaserAddressAndPhone;

    /**
     * 购买方开户行及账号
     */
    private String purchaserBankAccount;

    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售方地址、电话
     */
    private String sellerAddressAndPhone;

    /**
     * 销售方开户行及账号
     */
    private String sellerBankAccount;

    /**
     * 销售方识别号
     */
    private String sellerIdentificationNumber;

    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 单位
     */
    private String unit;

    /**
     * 数量
     */
    private String count;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 是否代开
     */
    private String whetherOtherOpen;

    /**
     * 机器编号
     */
    private String machineNo;

    /**
     * 成品油标志
     */
    private String productOilMark;

    /**
     * 市
     */
    private String city;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 通行费标志
     */
    private String tollSign;

    /**
     * 车船税
     */
    private String vehicleAndVesselTax;

    //-----------------------------------------------------------------
    /**
     * 是否专用发票
     */
    private Boolean special;


    @Override
    public String additionalForVerify() {
        if (Boolean.TRUE.equals(this.special)) {
            return this.amount;
        }else {
            int cutOutDigits = 6;
            if (StringUtils.isNotBlank(this.checkCode) && this.checkCode.length()>=cutOutDigits) {
                int length = this.checkCode.length();
                return this.checkCode.substring(length-cutOutDigits,length);
            }
            return null;
        }
    }

    @Override
    public String billDateForVerify() {
        if (StringUtils.isNotBlank(this.getInvoiceDate())) {
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
        if (StringUtils.isNotBlank(this.getInvoiceNo())) {
            return getInvoiceNo().replace("No","");
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal amountWithTax() {
        if (StringUtils.isNotBlank(this.getAmountInFigures())) {
            String money = this.getAmountInFigures();
            money = money.replace("¥","");
            return new BigDecimal(money);
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
        if (StringUtils.isNotBlank(this.invoiceNo)) {
            return this.invoiceNo.replace("No","");
        }else{
            return null;
        }
    }

    @Override
    public String code() {
        return this.invoiceCode;
    }

    @Override
    public BigDecimal taxAmount() {
        String s = "*";
        if (StringUtils.isNotBlank(this.taxAmount) && !this.taxAmount.contains(s)) {
            return new BigDecimal(this.taxAmount);
        }
        return null;
    }

    @Override
    public BigDecimal noTaxAmount() {
        if (StringUtils.isNotBlank(this.amount)) {
            return new BigDecimal(this.amount);
        }else{
            return null;
        }
    }

    @Override
    public String content() {
        return this.billContent;
    }
}
