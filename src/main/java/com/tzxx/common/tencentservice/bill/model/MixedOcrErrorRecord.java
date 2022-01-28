package com.tzxx.common.tencentservice.bill.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangliang
 * @date 2021/1/30.
 */
@Data
public class MixedOcrErrorRecord {
    private String imageUrl;
    private String imageBase64;
    private String errorMsg;
    private String errorCode;
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
