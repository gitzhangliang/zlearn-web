package com.tzxx.common.tencentservice.bill.model;

import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */
@Data
public class GeneralBasicOcrSuccessRecord {
    private GeneralBasicOCRResponse response;
    private String imageUrl;
    private String imageBase64;
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
