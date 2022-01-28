package com.tzxx.common.tencentservice.bill.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/1/30.
 */
@Data
public class ConcurrentMixedInvoiceIdentifyResult {
    private List<MixedOcrSuccessRecord> successRecords = new ArrayList<>();
    private List<MixedOcrErrorRecord> errorRecords = new ArrayList<>();
}
