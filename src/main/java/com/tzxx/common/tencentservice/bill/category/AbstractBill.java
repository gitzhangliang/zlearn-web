package com.tzxx.common.tencentservice.bill.category;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangliang
 * @date 2021/2/20.
 */
@Data
public abstract class AbstractBill implements Bill {

    private String id;

    private Long type;

    /**
     * 真伪
     */
    private Boolean authenticity = true;

    @Override
    public String id() {
        return getId();
    }

    @Override
    public boolean authenticity() {
        return authenticity;
    }

    @Override
    public void settingAuthenticity(Boolean authenticity) {
        this.authenticity = authenticity;
    }

    @Override
    public void settingType(Long type) {
        this.type = type;
    }

    @Override
    public Long type() {
        return type;
    }

    @Override
    public void settingId(String id) {
        this.id = id;
    }

    @Override
    public BigDecimal taxAmount() {
        return null;
    }

    @Override
    public BigDecimal noTaxAmount() {
        return amountWithTax();
    }

    @Override
    public Date date() {
        try {
            return invoiceDate();
        } catch (Exception e) {
            return null;
        }
    }

    abstract Date invoiceDate();

    @Override
    public BigDecimal includedTaxAmount() {
        try {
            return amountWithTax();
        } catch (Exception e) {
            return null;
        }
    }

    abstract BigDecimal amountWithTax();

}
