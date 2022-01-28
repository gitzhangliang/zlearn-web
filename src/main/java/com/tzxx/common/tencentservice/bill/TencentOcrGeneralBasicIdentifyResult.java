package com.tzxx.common.tencentservice.bill;

import com.tzxx.common.tencentservice.bill.model.GeneralBasicOcrErrorRecord;
import com.tzxx.common.tencentservice.bill.resolver.GeneralBasicOcrResponseResolverResult;
import lombok.Data;

import java.util.List;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */
@Data
public class TencentOcrGeneralBasicIdentifyResult {
    private List<GeneralBasicOcrResponseResolverResult> resolverResults;
    private List<GeneralBasicOcrErrorRecord> errorRecords;
}
