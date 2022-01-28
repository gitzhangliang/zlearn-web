package com.tzxx.common.tencentservice.bill.resolver;

import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.TextDetection;

/**
 * @author zhangliang
 * @date 2021/2/5.
 */
public class GeneralBasicOcrResponseResolver {
    public GeneralBasicOcrResponseResolverResult resolver(GeneralBasicOCRResponse resp,String imageUrl, String imageBase64){
        GeneralBasicOcrResponseResolverResult result = new GeneralBasicOcrResponseResolverResult();
        result.setImageUrl(imageUrl);
        result.setImageBase64(imageBase64);
        TextDetection[] textDetections = resp.getTextDetections();
        if (textDetections == null || textDetections.length == 0) {
            return result;
        }
        for (TextDetection textDetection : textDetections) {
            result.getContents().add(textDetection.getDetectedText());
        }
        return result;
    }
}
