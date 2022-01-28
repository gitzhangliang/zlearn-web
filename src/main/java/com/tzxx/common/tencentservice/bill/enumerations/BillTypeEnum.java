package com.tzxx.common.tencentservice.bill.enumerations;

import com.tzxx.common.domain.response.EnumResp;
import com.tzxx.common.tencentservice.bill.identifyhandler.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
@Getter
public enum BillTypeEnum {
    //票据类型
    UNKNOWN_TYPE(-1L,"未知类型",null),
    TAXI(0L,"出租车发票", TaxiSingleInvoiceInfoHandler.class),
    QUOTA(1L,"定额发票", QuotaSingleInvoiceInfoHandler.class),
    TRAIN(2L,"火车票", TrainSingleInvoiceInfoHandler.class),
    VAT(3L,"增值税发票", VatSingleInvoiceInfoHandler.class),
    AIR(5L,"机票行程单", AirSingleInvoiceInfoHandler.class),
    GENERAL_MACHINE_PRINT(8L,"通用机打发票", GeneralMachinePrintSingleInvoiceInfoHandler.class),
    CAR(9L,"汽车票", CarSingleInvoiceInfoHandler.class),
    SHIP(10L,"轮船票",ShipSingleInvoiceInfoHandler.class),
    VAT_ROLL(11L,"增值税发票（卷票 ）",VatRollSingleInvoiceInfoHandler.class),
    BUY_CAR(12L,"购车发票", BuyCarSingleInvoiceInfoHandler.class),
    ROAD_BRIDGE(13L,"过路过桥费发票",RoadBridgeSingleInvoiceInfoHandler.class);

    private final Long type;
    private final String name;
    private final Class<?> handleClass;

    BillTypeEnum(Long type,String name,Class<?> handleClass){
        this.name = name;
        this.type = type;
        this.handleClass = handleClass;
    }
    public static Class<?> getHandleClassByType(Long type){
        for(BillTypeEnum e:BillTypeEnum.values()){
            if(type.equals(e.type)){
                return e.handleClass;
            }
        }
        return null;
    }
    public static String getNameByType(Long type){
        for(BillTypeEnum e:BillTypeEnum.values()){
            if(type.equals(e.type)){
                return e.name;
            }
        }
        return null;
    }

    public static EnumResp toEnumResp() {
        EnumResp resp = new EnumResp();
        resp.setResult(new ArrayList<>());
        for(BillTypeEnum e:BillTypeEnum.values()){
            if(-1 != e.type){
                Map<String,Object> map =  new HashMap<>();
                map.put("id",e.getType());
                map.put("name",e.getName());
                resp.getResult().add(map);
            }
        }
        return resp;
    }
}
