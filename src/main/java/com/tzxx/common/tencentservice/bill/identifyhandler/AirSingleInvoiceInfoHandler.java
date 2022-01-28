package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.AirBill;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class AirSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<AirBill>{

    public AirSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected AirBill doHandle(Map<String, String> nameValueMap) {
        AirBill airBill = new AirBill();
        airBill.setTotalAmount(nameValueMap.get("合计金额"));
        airBill.setTicketPrice(nameValueMap.get("票价"));
        airBill.setFuelSurcharge(nameValueMap.get("燃油附加费"));
        airBill.setPrintTheSerialNumber(nameValueMap.get("印刷序号"));
        airBill.setOpeningDate(nameValueMap.get("填开日期"));
        return airBill;
    }
}
