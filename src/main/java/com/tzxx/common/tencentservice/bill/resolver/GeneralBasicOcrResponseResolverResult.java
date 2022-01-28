package com.tzxx.common.tencentservice.bill.resolver;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */

@Data
public class GeneralBasicOcrResponseResolverResult {
    private String imageUrl;
    private String imageBase64;
    private List<String> contents = new ArrayList<>();
    public String getImageInfo(){
        return StringUtils.isNotBlank(imageUrl) ? imageUrl : imageBase64;
    }
}
