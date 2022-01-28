package com.tzxx.common.tencentservice.bill;

import com.tzxx.common.tencentservice.bill.model.MixedOcrErrorRecord;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerifyErrorRecord;
import com.tzxx.common.tencentservice.bill.resolver.MixedInvoiceOcrResponseResolverResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/1/29.
 */
@Data
public class TencentOcrMixedInvoiceIdentifyResult {
    /**
     * 校验失败的增值税票（包含增值税卷票）
     */
    private List<VatInvoiceVerifyErrorRecord> verifyFailVatInvoices = new ArrayList<>();

    /**
     * 成功识别的数据,仅代表不触发异常
     */
    private List<MixedInvoiceOcrResponseResolverResult> resolverResults = new ArrayList<>();

    /**
     * 触发识别异常的数据
     */
    private List<MixedOcrErrorRecord> errorRecords;
}
