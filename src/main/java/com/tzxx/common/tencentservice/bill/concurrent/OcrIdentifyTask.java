package com.tzxx.common.tencentservice.bill.concurrent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.models.MixedInvoiceOCRResponse;
import com.tzxx.common.tencentservice.bill.model.ConcurrentMixedInvoiceIdentifyResult;
import com.tzxx.common.tencentservice.bill.model.MixedOcrErrorRecord;
import com.tzxx.common.tencentservice.bill.model.MixedOcrSuccessRecord;
import com.tzxx.common.tencentservice.bill.util.OcrUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author tzxx
 */
@Setter
@Slf4j
public class OcrIdentifyTask implements Runnable {
    private String imageUrl;
    private String imageBase64;
    private List<Long> types;
    private List<MixedOcrSuccessRecord> successRecords;
    private List<MixedOcrErrorRecord> errorRecords;
    private OcrFunction function;
    private CountDownLatch notifyDownLatch;

    @Override
    public void run() {
        try {
            MixedInvoiceOCRResponse response = runTask();
            MixedOcrSuccessRecord record = new MixedOcrSuccessRecord();
            record.setImageUrl(imageUrl);
            record.setImageBase64(imageBase64);
            record.setResponse(response);
            successRecords.add(record);
        } catch (TencentCloudSDKException e) {
            MixedOcrErrorRecord errorRecord = new MixedOcrErrorRecord();
            errorRecord.setImageUrl(imageUrl);
            errorRecord.setImageBase64(imageBase64);
            errorRecord.setErrorMsg(e.getMessage());
            errorRecord.setErrorCode(e.getErrorCode());
            errorRecords.add(errorRecord);
        } finally {
            notifyDownLatch.countDown();
        }
    }

    /**
     * 调用腾讯云接口，若错误码是超出接口频率限制，则休眠后重试
     */
    private MixedInvoiceOCRResponse runTask() throws TencentCloudSDKException {
        try {
            return function.apply(imageUrl, imageBase64, types);
        } catch (TencentCloudSDKException e) {
            if(OcrUtil.REQUEST_LIMIT_EXCEEDED_ERROR_CODE.equals(e.getErrorCode())){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    log.error("OcrIdentifyTask RequestLimitExceeded runTask InterruptedException:{0}", e);
                    Thread.currentThread().interrupt();
                }
                return runTask();
            }else{
                throw e;
            }
        }
    }

    public static OcrIdentifyTask createOcrIdentifyTask(boolean byImageUrl, String image, List<Long> types,
                                                        ConcurrentMixedInvoiceIdentifyResult result,
                                                        CountDownLatch mainCountDownLatch,OcrFunction function) {
        OcrIdentifyTask task = new OcrIdentifyTask();
        if (Boolean.TRUE.equals(byImageUrl)) {
            task.setImageUrl(image);
        } else {
            task.setImageBase64(image);
        }
        task.setTypes(types);
        task.setNotifyDownLatch(mainCountDownLatch);
        task.setFunction(function);
        task.setSuccessRecords(result.getSuccessRecords());
        task.setErrorRecords(result.getErrorRecords());
        return task;
    }

    public interface OcrFunction {
        MixedInvoiceOCRResponse apply(String imageUrl, String imageBase64, List<Long> types) throws TencentCloudSDKException;
    }
}