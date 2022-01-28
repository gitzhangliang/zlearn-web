package com.tzxx.common.tencentservice.bill;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzxx.common.tencentservice.bill.model.*;

import com.zl.utils.JsonUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
@Service("tencentOcrLogService")
public class TencentOcrLogServiceImpl extends ServiceImpl<TencentOcrLogMapper, TencentOcrLog> implements TencentOcrLogService {

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordVerifyLog(Long userId,ConcurrentVatInvoiceVerifyResult verifyResult, String token){
        List<TencentOcrLog> logs = new ArrayList<>();
        for (VatInvoiceVerifyErrorRecord errorRecord : verifyResult.getErrorRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token,"vatInvoiceVerify",
                    getVerifyParam(errorRecord.getInvoiceCode(), errorRecord.getInvoiceNo(), errorRecord.getInvoiceDate(), errorRecord.getAdditional(), errorRecord.getImageUrl()),false);
            tencentOcrLog.setErrorMsg(errorRecord.getErrorMsg());
            tencentOcrLog.setCode(errorRecord.getErrorCode());
            logs.add(tencentOcrLog);
        }
        for (VatInvoiceVerifySuccessRecord successRecord : verifyResult.getSuccessRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token, "vatInvoiceVerify",
                    getVerifyParam(successRecord.getInvoiceCode(), successRecord.getInvoiceNo(), successRecord.getInvoiceDate(), successRecord.getAdditional(), successRecord.getImageUrl()), true);
            logs.add(tencentOcrLog);
        }
        saveBatch(logs);
    }

    private String getVerifyParam(String invoiceCode, String invoiceNo, String invoiceDate, String additional, String imageUrl){
        VatInvoiceVerify vatInvoiceVerify = new VatInvoiceVerify();
        vatInvoiceVerify.setInvoiceCode(invoiceCode);
        vatInvoiceVerify.setInvoiceNo(invoiceNo);
        vatInvoiceVerify.setInvoiceDate(invoiceDate);
        vatInvoiceVerify.setAdditional(additional);
        vatInvoiceVerify.setImageUrl(imageUrl);
        return JsonUtil.objToStr(vatInvoiceVerify);
    }


    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordMixedIdentifyLog(Long userId,ConcurrentMixedInvoiceIdentifyResult result, String token){
        List<TencentOcrLog> logs = new ArrayList<>();
        for (MixedOcrErrorRecord errorRecord : result.getErrorRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token,"mixedInvoiceIdentify",errorRecord.getImageUrl(),false);
            tencentOcrLog.setErrorMsg(errorRecord.getErrorMsg());
            tencentOcrLog.setCode(errorRecord.getErrorCode());
            logs.add(tencentOcrLog);
        }
        for (MixedOcrSuccessRecord successRecord : result.getSuccessRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token,"mixedInvoiceIdentify",successRecord.getImageUrl(),true);
            tencentOcrLog.setResp(JsonUtil.objToStr(successRecord.getResponse()));
            logs.add(tencentOcrLog);
        }
        saveBatch(logs);
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordGeneralBasicIdentifyLog(Long userId,ConcurrentGeneralBasicIdentifyResult result, String token) {
        List<TencentOcrLog> logs = new ArrayList<>();
        for (GeneralBasicOcrErrorRecord errorRecord : result.getErrorRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token,"concurrentGeneralBasicIdentify",errorRecord.getImageUrl(),false);
            tencentOcrLog.setErrorMsg(errorRecord.getErrorMsg());
            tencentOcrLog.setCode(errorRecord.getErrorCode());
            logs.add(tencentOcrLog);
        }
        for (GeneralBasicOcrSuccessRecord successRecord : result.getSuccessRecords()) {
            TencentOcrLog tencentOcrLog = getTencentOcrLogAndSettingPublicField(userId,token,"concurrentGeneralBasicIdentify",successRecord.getImageUrl(),true);
            tencentOcrLog.setResp(JsonUtil.objToStr(successRecord.getResponse()));
            logs.add(tencentOcrLog);
        }
        saveBatch(logs);
    }

    private TencentOcrLog getTencentOcrLogAndSettingPublicField(Long userId,String token,String interfaceName,String requestParam,boolean success){
        TencentOcrLog tencentOcrLog = new TencentOcrLog();
        tencentOcrLog.setToken(token);
        tencentOcrLog.setUserId(userId);
        tencentOcrLog.setInterfaceName(interfaceName);
        tencentOcrLog.setRequestParam(requestParam);
        tencentOcrLog.setSuccess(success);
        return tencentOcrLog;
    }
}
