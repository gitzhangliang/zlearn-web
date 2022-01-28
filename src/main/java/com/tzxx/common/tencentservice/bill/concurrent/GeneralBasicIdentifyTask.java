package com.tzxx.common.tencentservice.bill.concurrent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.tzxx.common.tencentservice.bill.model.ConcurrentGeneralBasicIdentifyResult;
import com.tzxx.common.tencentservice.bill.model.GeneralBasicOcrErrorRecord;
import com.tzxx.common.tencentservice.bill.model.GeneralBasicOcrSuccessRecord;
import com.tzxx.common.tencentservice.bill.util.OcrUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author tzxx
 */
@Slf4j
@Setter
public class GeneralBasicIdentifyTask implements Runnable {
    private String imageUrl;
    private String imageBase64;
    private List<GeneralBasicOcrSuccessRecord> successRecords;
    private List<GeneralBasicOcrErrorRecord> errorRecords;
    private GeneralBasicIdentifyFunction function;
    private CountDownLatch notifyDownLatch;

    @Override
    public void run() {
        try {
            GeneralBasicOCRResponse response = runTask();
            GeneralBasicOcrSuccessRecord record = new GeneralBasicOcrSuccessRecord();
            record.setImageUrl(imageUrl);
            record.setImageBase64(imageBase64);
            record.setResponse(response);
            successRecords.add(record);
        } catch (TencentCloudSDKException e) {
            GeneralBasicOcrErrorRecord errorRecord = new GeneralBasicOcrErrorRecord();
            errorRecord.setErrorMsg(e.getMessage());
            errorRecord.setErrorCode(e.getErrorCode());
            errorRecord.setImageUrl(imageUrl);
            errorRecord.setImageBase64(imageBase64);
            errorRecords.add(errorRecord);
        } finally {
            notifyDownLatch.countDown();
        }
    }

    /**
     * 调用腾讯云接口，若错误码是超出接口频率限制，则休眠后重试
     */
    private GeneralBasicOCRResponse runTask() throws TencentCloudSDKException {
        try {
            return function.apply(imageUrl,imageBase64);
        } catch (TencentCloudSDKException e) {
            if(OcrUtil.REQUEST_LIMIT_EXCEEDED_ERROR_CODE.equals(e.getErrorCode())){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    log.error("GeneralBasicIdentifyTask RequestLimitExceeded runTask InterruptedException:{0}", e);
                    Thread.currentThread().interrupt();
                }
                return runTask();
            }else{
                throw e;
            }
        }
    }

    public static GeneralBasicIdentifyTask createGeneralBasicIdentifyTask(boolean byImageUrl,String image,
                                                                          ConcurrentGeneralBasicIdentifyResult result,
                                                                          CountDownLatch mainCountDownLatch,
                                                                          GeneralBasicIdentifyFunction function){
        GeneralBasicIdentifyTask task = new GeneralBasicIdentifyTask();
        if (Boolean.TRUE.equals(byImageUrl)) {
            task.setImageUrl(image);
        } else {
            task.setImageBase64(image);
        }
        task.setFunction(function);
        task.setNotifyDownLatch(mainCountDownLatch);
        task.setSuccessRecords(result.getSuccessRecords());
        task.setErrorRecords(result.getErrorRecords());
        return task;
    }
    public interface GeneralBasicIdentifyFunction {
        /**通用文字识别
         * @param imageUrl 图片/pdf信息
         * @param imageBase64 图片base64
         * @return GeneralBasicOCRResponse
         * @throws TencentCloudSDKException 文字识别异常
         */
        GeneralBasicOCRResponse apply(String imageUrl, String imageBase64) throws TencentCloudSDKException;
    }
}