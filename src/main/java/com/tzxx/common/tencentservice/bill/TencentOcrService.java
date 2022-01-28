package com.tzxx.common.tencentservice.bill;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoice;
import com.tencentcloudapi.ocr.v20181119.models.VatInvoiceVerifyResponse;

import com.tzxx.common.tencentservice.bill.enumerations.BillTypeEnum;
import com.tzxx.common.tencentservice.bill.enumerations.BillVerifyTypeEnum;
import com.tzxx.common.tencentservice.bill.enumerations.VerifyFailCodeEnum;
import com.tzxx.common.tencentservice.bill.model.*;
import com.tzxx.common.tencentservice.bill.resolver.GeneralBasicOcrResponseResolver;
import com.tzxx.common.tencentservice.bill.resolver.GeneralBasicOcrResponseResolverResult;
import com.tzxx.common.tencentservice.bill.resolver.MixedInvoiceOcrResponseResolver;
import com.tzxx.common.tencentservice.bill.resolver.MixedInvoiceOcrResponseResolverResult;
import com.tzxx.common.tencentservice.bill.util.OcrUtil;

import com.zl.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhangliang
 * @date 2021/1/28.
 */
@Slf4j
@Service
public class TencentOcrService {
    @Value(value = "${tencent-ocr-secret-id}")
    private String tencentSecretId;
    @Value(value = "${tencent-ocr-secret-key}")
    private String tencentSecretKey;
    @Value(value = "${tencent-ocr-region}")
    private String tencentRegion;
    @Resource
    private TencentOcrLogServiceImpl tencentOcrLogService;


    @PostConstruct
    public void startOcrClient(){
        OcrUtil.initOcrClient(tencentSecretId,tencentSecretKey,tencentRegion);
    }

    /**
     * 多张混贴票据图片识别
     */
    public TencentOcrMixedInvoiceIdentifyResult mixedInvoiceIdentify(boolean byImageUrl,List<String> imageUrls,List<Long> types,boolean validateVatInvoice){
        String token = UUID.randomUUID().toString();
        TencentOcrMixedInvoiceIdentifyResult result = new TencentOcrMixedInvoiceIdentifyResult();
        //同时识别多张票据图片
        ConcurrentMixedInvoiceIdentifyResult concurrentResult = OcrUtil.getInstance().concurrentMixedInvoiceIdentify(byImageUrl, imageUrls, types);
        tencentOcrLogService.recordMixedIdentifyLog(1L,concurrentResult,token);
        //记录异常数据
        result.setErrorRecords(concurrentResult.getErrorRecords());
        //解析成功（不触发TencentCloudSDKException）识别结果，
        List<MixedOcrSuccessRecord> successRecords = concurrentResult.getSuccessRecords();
        List<MixedInvoiceOcrResponseResolverResult> resolverResults = new ArrayList<>();
        for (MixedOcrSuccessRecord successRecord : successRecords) {
            //解析识别结果，构造不同类型的发票实体。以及统计成功失败张数（不触发异常，但返回的数据中也可能失败,code不是OK）
            resolverResults.add(new MixedInvoiceOcrResponseResolver().resolver(successRecord.getResponse(),successRecord.getImageUrl(),successRecord.getImageBase64()));
        }
        result.getResolverResults().addAll(resolverResults);
        if (Boolean.TRUE.equals(validateVatInvoice)) {
            //合并所有图片中的增值税发票并校验
            List<VatInvoiceVerify> vatInvoiceVerifies = MixedInvoiceOcrResponseResolverResult.mergeVatInvoiceForVerify(resolverResults);
            ConcurrentVatInvoiceVerifyResult verifyResult = OcrUtil.getInstance().concurrentVatInvoiceVerify(vatInvoiceVerifies);
            tencentOcrLogService.recordVerifyLog(1L,verifyResult,token);
            //处理失败校验的增值税发票
            result.getVerifyFailVatInvoices().addAll(verifyResult.getErrorRecords());
            MixedInvoiceOcrResponseResolverResult.markVerifyFailData(verifyResult.getErrorRecords(),resolverResults);
        }
        return result;
    }

    /**
     * 校验增值税发票
     */
    public TencentVerifyResult vatInvoiceVerify(String invoiceCode, String invoiceNo, String invoiceDate, String additional){
        TencentVerifyResult result = new TencentVerifyResult();
        boolean success = false;
        TencentOcrLog tencentOcrLog = new TencentOcrLog();
        tencentOcrLog.setToken(UUID.randomUUID().toString());
        tencentOcrLog.setUserId(1L);
        tencentOcrLog.setInterfaceName("vatInvoiceVerify");
        tencentOcrLog.setRequestParam(JsonUtil.objToStr(new VatInvoiceVerify(invoiceCode,invoiceNo,invoiceDate,additional)));
        try {
            VatInvoiceVerifyResponse response = OcrUtil.getInstance().vatInvoiceVerify(invoiceCode, invoiceNo, invoiceDate, additional);
            VatInvoice invoice = response.getInvoice();
            result.setNoTaxAmount(new BigDecimal(invoice.getAmountWithoutTax()));
            result.setAmount(new BigDecimal(invoice.getAmountWithTax()));
            String s = "*";
            if (StringUtils.isNotBlank(invoice.getTaxAmount()) && !invoice.getTaxAmount().contains(s)) {
                result.setTaxAmount(new BigDecimal(invoice.getTaxAmount()));
            }
            result.setCheckCode(invoice.getCheckCode());
            BillTypeEnum billType = BillVerifyTypeEnum.getBillTypeEnumByType(invoice.getType());
            result.setType(billType!=null ? billType.getType() : null);
            success = true;
        } catch (TencentCloudSDKException e) {
            tencentOcrLog.setErrorMsg(e.getMessage());
            tencentOcrLog.setCode(e.getErrorCode());
            result.setErrorMsg(e.getMessage());
            result.setCustomMsg(VerifyFailCodeEnum.getCustomMessageByCode(e.getErrorCode()));
            if(StringUtils.isBlank(result.getCustomMsg())){
                result.setCustomMsg(VerifyFailCodeEnum.FAILED_OPERATION_UN_KNOW_ERROR.getCustomMessage());
            }
            log.error("调用腾讯云OCR增值税发票核验接口失败：{0}",e);
        }
        tencentOcrLog.setSuccess(success);
        tencentOcrLogService.save(tencentOcrLog);
        result.setSuccess(success);
        return result;
    }

    /**
     * 多张通用文字图片/pdf识别
     */
    public TencentOcrGeneralBasicIdentifyResult concurrentGeneralBasicIdentify(boolean byImageUrl,List<String> imageUrls){
        String token = UUID.randomUUID().toString();
        TencentOcrGeneralBasicIdentifyResult result = new TencentOcrGeneralBasicIdentifyResult();
        //同时识别多张票
        ConcurrentGeneralBasicIdentifyResult concurrentResult = OcrUtil.getInstance().concurrentGeneralBasicIdentify(byImageUrl,imageUrls);
        tencentOcrLogService.recordGeneralBasicIdentifyLog(1L,concurrentResult,token);
        //记录异常数据
        result.setErrorRecords(concurrentResult.getErrorRecords());
        //解析成功（不触发TencentCloudSDKException）识别结果，
        List<GeneralBasicOcrSuccessRecord> successRecords = concurrentResult.getSuccessRecords();
        List<GeneralBasicOcrResponseResolverResult> resolverResults = new ArrayList<>();
        for (GeneralBasicOcrSuccessRecord successRecord : successRecords) {
            resolverResults.add(new GeneralBasicOcrResponseResolver().resolver(successRecord.getResponse(),successRecord.getImageUrl(),successRecord.getImageBase64()));
        }
        result.setResolverResults(resolverResults);
        return result;
    }
}
