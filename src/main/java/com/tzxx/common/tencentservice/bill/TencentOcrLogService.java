package com.tzxx.common.tencentservice.bill;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzxx.common.tencentservice.bill.model.ConcurrentGeneralBasicIdentifyResult;
import com.tzxx.common.tencentservice.bill.model.ConcurrentMixedInvoiceIdentifyResult;
import com.tzxx.common.tencentservice.bill.model.ConcurrentVatInvoiceVerifyResult;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
public interface TencentOcrLogService extends IService<TencentOcrLog> {

    void recordVerifyLog(Long userId,ConcurrentVatInvoiceVerifyResult verifyResult, String token);

    void recordMixedIdentifyLog(Long userId,ConcurrentMixedInvoiceIdentifyResult result, String token);

    void recordGeneralBasicIdentifyLog(Long userId,ConcurrentGeneralBasicIdentifyResult concurrentResult, String token);
}
