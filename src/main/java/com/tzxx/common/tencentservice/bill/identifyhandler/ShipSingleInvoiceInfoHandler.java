package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.ShipBill;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class ShipSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<ShipBill>{

    public ShipSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected ShipBill doHandle(Map<String, String> nameValueMap) {
        ShipBill shipBill = new ShipBill();
        shipBill.setTicketPrice(nameValueMap.get("票价"));
        shipBill.setInvoiceNo(nameValueMap.get("发票号码"));
        shipBill.setInvoiceCode(nameValueMap.get("发票代码"));
        shipBill.setDate(nameValueMap.get("日期"));
        return shipBill;
    }
}
