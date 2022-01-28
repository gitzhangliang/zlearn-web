package com.tzxx.common.tencentservice.bill.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */
@Data
public class ConcurrentGeneralBasicIdentifyResult {
    private List<GeneralBasicOcrSuccessRecord> successRecords = new ArrayList<>();
    private List<GeneralBasicOcrErrorRecord> errorRecords = new ArrayList<>();
}
