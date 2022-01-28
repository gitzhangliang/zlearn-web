package com.tzxx.common.tencentservice.bill.model;

import com.tencentcloudapi.ocr.v20181119.models.MixedInvoiceOCRResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/2/1.
 */
@Data
public class MixedOcrSuccessRecord {
    private String imageUrl;
    private String imageBase64;
    private MixedInvoiceOCRResponse response;
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
