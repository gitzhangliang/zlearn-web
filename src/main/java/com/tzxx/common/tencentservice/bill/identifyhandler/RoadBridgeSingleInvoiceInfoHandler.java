package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.RoadBridgeBill;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public class RoadBridgeSingleInvoiceInfoHandler extends AbstractSingleInvoiceInfoHandler<RoadBridgeBill>{

    public RoadBridgeSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        super(singleInvoiceInfos);
    }

    @Override
    protected RoadBridgeBill doHandle(Map<String, String> nameValueMap) {
        RoadBridgeBill roadBridgeBill = new RoadBridgeBill();
        roadBridgeBill.setAmount(nameValueMap.get("金额"));
        roadBridgeBill.setInvoiceNo(nameValueMap.get("发票号码"));
        roadBridgeBill.setInvoiceCode(nameValueMap.get("发票代码"));
        roadBridgeBill.setDate(nameValueMap.get("日期"));
        return roadBridgeBill;
    }
}
