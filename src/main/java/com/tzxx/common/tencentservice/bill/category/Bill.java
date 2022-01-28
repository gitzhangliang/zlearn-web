package com.tzxx.common.tencentservice.bill.category;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangliang
 * @date 2021/1/29.
 */
public interface Bill {

    /**
     * 生成的票据唯一标识
     * @return 唯一标识
     */
    String id();

    /**
     * 唯一标识设置
     * @param id 唯一标识
     */
    void settingId(String id);

    /**
     * 真伪
     * @return true 真  false 假
     */
    boolean authenticity();

    /**
     * 真伪设置
     * @param authenticity 真伪
     */
    void settingAuthenticity(Boolean authenticity);

    /**
     * 获取类型
     * @return  type 类型
     */
    Long type();

    /**
     * 类型设置
     * @param type 类型
     */
    void settingType(Long type);

    /**
     * 发票日期
     * @return  Date
     */
    Date date();

    /**
     * 发票编号
     * @return  String
     */
    String number();

    /**
     * 发票代码
     * @return  String
     */
    String code();

    /**
     * 税额
     * @return  BigDecimal
     */
    BigDecimal taxAmount();

    /**
     * 不含税金额
     * @return  BigDecimal
     */
    BigDecimal noTaxAmount();

    /**
     * 含税金额：票据可报销的金额
     * @return 金额
     */
    BigDecimal includedTaxAmount();

    /**
     * 票据内容
     * @return  String
     */
    String content();

}
