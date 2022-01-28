package com.tzxx.common.tencentservice.bill.concurrent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceVerifyResponse;
import com.tzxx.common.tencentservice.bill.model.ConcurrentVatInvoiceVerifyResult;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerify;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerifyErrorRecord;
import com.tzxx.common.tencentservice.bill.model.VatInvoiceVerifySuccessRecord;
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
public class VatInvoiceVerifyTask implements Runnable {
    private String billId;
    private String invoiceCode;
    private String invoiceNo;
    private String invoiceDate;
    private String additional;
    private String imageUrl;
    private String imageBase64;
    private List<VatInvoiceVerifySuccessRecord> successRecords;
    private List<VatInvoiceVerifyErrorRecord> errorRecords;
    private VatInvoiceVerifyFunction function;
    private CountDownLatch notifyDownLatch;

    @Override
    public void run() {
        try {
            VatInvoiceVerifyResponse response = runTask();
            VatInvoiceVerifySuccessRecord record = new VatInvoiceVerifySuccessRecord();
            record.setImageUrl(imageUrl);
            record.setImageBase64(imageBase64);
            record.setInvoiceCode(invoiceCode);
            record.setInvoiceNo(invoiceNo);
            record.setInvoiceDate(invoiceDate);
            record.setAdditional(additional);
            record.setResponse(response);
            record.setBillId(billId);
            successRecords.add(record);
        } catch (TencentCloudSDKException e) {
            VatInvoiceVerifyErrorRecord errorRecord = new VatInvoiceVerifyErrorRecord();
            errorRecord.setErrorMsg(e.getMessage());
            errorRecord.setErrorCode(e.getErrorCode());
            errorRecord.setImageUrl(imageUrl);
            errorRecord.setImageBase64(imageBase64);
            errorRecord.setInvoiceCode(invoiceCode);
            errorRecord.setInvoiceNo(invoiceNo);
            errorRecord.setInvoiceDate(invoiceDate);
            errorRecord.setAdditional(additional);
            errorRecord.setBillId(billId);
            errorRecords.add(errorRecord);
        } finally {
            notifyDownLatch.countDown();
        }
    }

    /**
     * 调用腾讯云接口，若错误码是超出接口频率限制，则休眠后重试
     */
    private VatInvoiceVerifyResponse runTask() throws TencentCloudSDKException {
        try {
            return function.apply(invoiceCode, invoiceNo, invoiceDate, additional);
        } catch (TencentCloudSDKException e) {
            if(OcrUtil.REQUEST_LIMIT_EXCEEDED_ERROR_CODE.equals(e.getErrorCode())){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    log.error("VatInvoiceVerifyTask RequestLimitExceeded runTask InterruptedException:{0}", e);
                    Thread.currentThread().interrupt();
                }
                return runTask();
            }else{
                throw e;
            }
        }
    }

    public static VatInvoiceVerifyTask createVatInvoiceVerifyTask(VatInvoiceVerify vatInvoiceVerify,
                                                                  ConcurrentVatInvoiceVerifyResult result,
                                                                  CountDownLatch mainCountDownLatch,
                                                                  VatInvoiceVerifyFunction function) {
        VatInvoiceVerifyTask task = new VatInvoiceVerifyTask();
        task.setImageUrl(vatInvoiceVerify.getImageUrl());
        task.setImageBase64(vatInvoiceVerify.getImageBase64());
        task.setInvoiceCode(vatInvoiceVerify.getInvoiceCode());
        task.setInvoiceNo(vatInvoiceVerify.getInvoiceNo());
        task.setInvoiceDate(vatInvoiceVerify.getInvoiceDate());
        task.setAdditional(vatInvoiceVerify.getAdditional());
        task.setFunction(function);
        task.setNotifyDownLatch(mainCountDownLatch);
        task.setSuccessRecords(result.getSuccessRecords());
        task.setErrorRecords(result.getErrorRecords());
        task.setBillId(vatInvoiceVerify.getBillId());
        return task;
    }

    public interface VatInvoiceVerifyFunction {
        VatInvoiceVerifyResponse apply(String invoiceCode, String invoiceNo, String invoiceDate, String additional) throws TencentCloudSDKException;
    }
}