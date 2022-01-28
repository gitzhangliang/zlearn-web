package com.tzxx.common.tencentservice.bill.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/2/1.
 */
@Data
public class ConcurrentVatInvoiceVerifyResult {
    private List<VatInvoiceVerifySuccessRecord> successRecords = new ArrayList<>();
    private List<VatInvoiceVerifyErrorRecord> errorRecords = new ArrayList<>();
}
