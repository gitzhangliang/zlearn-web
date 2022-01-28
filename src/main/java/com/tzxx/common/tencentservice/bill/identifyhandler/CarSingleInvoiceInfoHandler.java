package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.CarBill;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class CarSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<CarBill>{

    public CarSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected CarBill doHandle(Map<String, String> nameValueMap) {
        CarBill carBill = new CarBill();
        carBill.setTicketPrice(nameValueMap.get("票价"));
        carBill.setInvoiceNo(nameValueMap.get("发票号码"));
        carBill.setInvoiceCode(nameValueMap.get("发票代码"));
        carBill.setDate(nameValueMap.get("日期"));
        return carBill;
    }
}
