package com.tzxx.common.tencentservice.bill.resolver;

import com.tencentcloudapi.ocr.v20181119.models.MixedInvoiceItem;
import com.tencentcloudapi.ocr.v20181119.models.MixedInvoiceOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.SingleInvoiceInfo;
import com.tzxx.common.tencentservice.bill.category.Bill;
import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.tzxx.common.tencentservice.bill.identifyhandler.AbstractSingleInvoiceInfoHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/1/29.
 */
@Slf4j
public class MixedInvoiceOcrResponseResolver {
    public MixedInvoiceOcrResponseResolverResult resolver(MixedInvoiceOCRResponse resp, String imageUrl, String imageBase64){
        MixedInvoiceOcrResponseResolverResult resolverResult = new MixedInvoiceOcrResponseResolverResult();
        resolverResult.setImageUrl(imageUrl);
        resolverResult.setImageBase64(imageBase64);
        try{
            MixedInvoiceItem[] mixedInvoiceItems = resp.getMixedInvoiceItems();
            if (mixedInvoiceItems == null || mixedInvoiceItems.length == 0) {
                return resolverResult;
            }
            for (MixedInvoiceItem mixedInvoiceItem : mixedInvoiceItems) {
                String code = mixedInvoiceItem.getCode();
                if ("OK".equals(code)) {
                    resolverResult.setSuccessCount(resolverResult.getSuccessCount()+1);
                    Long type = mixedInvoiceItem.getType();
                    SingleInvoiceInfo[] singleInvoiceInfos = mixedInvoiceItem.getSingleInvoiceInfos();
                    List<SingleInvoiceInfo> singleInvoiceInfoList = Arrays.asList(singleInvoiceInfos);
                    Class<?> handleClassByType = BillTypeEnum.getHandleClassByType(type);
                    if (handleClassByType != null) {
                        Constructor<?> constructor = handleClassByType.getDeclaredConstructor(List.class);
                        Object o = constructor.newInstance(singleInvoiceInfoList);
                        if (o instanceof AbstractSingleInvoiceInfoHandler) {
                            AbstractSingleInvoiceInfoHandler<?> handler = (AbstractSingleInvoiceInfoHandler<?>) o;
                            Bill bill = handler.handle();
                            bill.settingType(type);
                            bill.settingId(handler.generateId());
                            resolverResult.getBills().add(bill);
                            resolverResult.getIdBillMap().put(bill.id(),bill);
                            List<Bill> bills = resolverResult.getBillMap().computeIfAbsent(type, f -> new ArrayList<>());
                            bills.add(bill);
                        }
                    }
                }else{
                    resolverResult.setErrorCount(resolverResult.getErrorCount()+1);
                }
            }
        }catch (Exception e){
            log.error("MixedInvoiceOcrResponseResolver Resolver Error:{0}",e);
        }
        return resolverResult;
    }

}
