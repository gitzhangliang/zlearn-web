package com.tzxx.common.tencentservice.bill.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */
@Data
public class GeneralBasicOcrErrorRecord {
    private String imageUrl;
    private String imageBase64;
    private String errorMsg;
    private String errorCode;
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
