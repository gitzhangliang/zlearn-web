package com.tzxx.common.tencentservice.bill.identifyhandler;

import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.Bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public abstract class AbstractSingleInvoiceInfoHandler<T extends Bill> {

    private final List<SingleInvoiceInfo> singleInvoiceInfos;

    protected AbstractSingleInvoiceInfoHandler(List<SingleInvoiceInfo> singleInvoiceInfos){
        this.singleInvoiceInfos = singleInvoiceInfos;
    }

    public T handle (){
        Map<String, String> map = preHandle();
        return doHandle(map);
    }
    protected abstract T doHandle(Map<String, String> nameValueMap);

    private Map<String,String> preHandle(){
        Map<String,String> map = new HashMap<>(16);
        for (SingleInvoiceInfo singleInvoiceInfo : singleInvoiceInfos) {
            map.put(singleInvoiceInfo.getName(),singleInvoiceInfo.getValue());
        }
        return map;
    }

    public String generateId(){
        return UUID.randomUUID().toString();
    }
}
