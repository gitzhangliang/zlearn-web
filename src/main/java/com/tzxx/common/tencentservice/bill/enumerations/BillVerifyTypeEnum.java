package com.tzxx.common.tencentservice.bill.enumerations;

import lombok.Getter;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
@Getter
public enum BillVerifyTypeEnum {

    //票据识别类型
    SPECIAL("01","专用发票",BillTypeEnum.VAT),
    FREIGHT("02","货运发票", null),
    MOTOR("03","机动车发票", null),
    COMMON("04","普通发票", BillTypeEnum.VAT),
    ELECTRONIC("10","电子发票", BillTypeEnum.VAT),
    ROLL("11","卷式发票", BillTypeEnum.VAT_ROLL),
    TOLL("14","通行费发票", BillTypeEnum.VAT),
    SECOND_HAND("15","二手车发票", null);

    private final String type;
    private final String name;
    private final BillTypeEnum billTypeEnum;

    BillVerifyTypeEnum(String type, String name, BillTypeEnum billTypeEnum){
        this.name = name;
        this.type = type;
        this.billTypeEnum = billTypeEnum;
    }

    public static String getNameByType(String type){
        for(BillVerifyTypeEnum e:BillVerifyTypeEnum.values()){
            if(type.equals(e.type)){
                return e.name;
            }
        }
        return null;
    }

    public static BillTypeEnum getBillTypeEnumByType(String type){
        for(BillVerifyTypeEnum e:BillVerifyTypeEnum.values()){
            if(type.equals(e.type)){
                return e.billTypeEnum;
            }
        }
        return null;
    }

}
