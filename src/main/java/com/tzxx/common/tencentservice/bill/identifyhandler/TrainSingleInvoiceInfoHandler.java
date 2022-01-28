package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.TrainBill;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class TrainSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<TrainBill>{

    public TrainSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected TrainBill doHandle(Map<String, String> nameValueMap) {
        TrainBill trainBill = new TrainBill();
        trainBill.setTicketPrice(nameValueMap.get("票价"));
        trainBill.setNumber(nameValueMap.get("编号"));
        trainBill.setDepartureTime(nameValueMap.get("出发时间"));
        return trainBill;
    }
}
