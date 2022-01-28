package com.tzxx.common.tencentservice.bill.util;

import com.google.common.collect.Lists;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.*;
import com.tzxx.common.tencentservice.bill.concurrent.*;
import com.tzxx.common.tencentservice.bill.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**由于腾讯云接口限制，目前采用的是分组执行（每组要执行的线程数量根据腾讯云具体接口限制决定）。
 * 组与组之间间隔时间由SCHEDULED_THREAD_POOL_EXECUTOR决定。
 * SCHEDULED_THREAD_POOL_EXECUTOR，采用单线程线程池。每一组的List<Runnable>要执行的线程put到
 * RUNNABLE_HOLDER_SCHEDULE_QUEUE队列中。SCHEDULED_THREAD_POOL_EXECUTOR会每隔1500毫秒取出一组需要执行的线程执行。
 * 通用文字识别是直接执行，因为根据业务需求此接口只有一人会使用
 * @author zhangliang
 * @date 2021/1/28.
 */
@Slf4j
public class OcrUtil {
    public static final String REQUEST_LIMIT_EXCEEDED_ERROR_CODE = "RequestLimitExceeded";
    private static final int MIXED_LIMIT_COUNT = 5;
    private static final int GENERAL_BASIC_LIMIT_COUNT = 20;
    private static final int VERIFY_LIMIT_ONE_SECOND = 20;
    private static final String ENDPOINT = "ocr.tencentcloudapi.com";
    private static final OcrUtil INSTANCE = new OcrUtil();
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(30, 50, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(200), new OcrThreadFactory("tencentOcrThreadFactory"), new OcrRejectHandler());

    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(1, new OcrThreadFactory("tencentOcrScheduledThreadFactory"), new OcrRejectHandler());
    private static final BlockingQueue<RunnableHolder> RUNNABLE_HOLDER_SCHEDULE_QUEUE = new LinkedBlockingQueue<>(1000);

    private static OcrClient client;

    private OcrUtil() {
    }


    static {
        invokeTaskForSameTimeManyThread();
    }


    public static OcrUtil getInstance() {
        return INSTANCE;
    }


    /**
     * 仅需要调用一次
     */
    public static void initOcrClient(String secretId, String secretKey, String region) {
        Credential cred = new Credential(secretId, secretKey);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(ENDPOINT);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        client = new OcrClient(cred, region, clientProfile);
    }

    private static void invokeTaskForSameTimeManyThread() {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(() -> {
            try {
                RunnableHolder holder = RUNNABLE_HOLDER_SCHEDULE_QUEUE.take();
                List<Runnable> tasks = holder.getTasks();
                for (Runnable task : tasks) {
                    EXECUTOR.submit(task);
                }
            } catch (InterruptedException e) {
                log.error("invokeTaskForSameTimeManyThread InterruptedException:{0}", e);
                Thread.currentThread().interrupt();
            }

        }, 0, 1500, TimeUnit.MILLISECONDS);
    }




    public ConcurrentMixedInvoiceIdentifyResult concurrentMixedInvoiceIdentify(boolean byImageUrl, List<String> images, List<Long> types) {
        ConcurrentMixedInvoiceIdentifyResult result = new ConcurrentMixedInvoiceIdentifyResult();
        CountDownLatch mainCountDownLatch = new CountDownLatch(images.size());
        List<Runnable> tasks = images.stream()
                .map(v -> OcrIdentifyTask.createOcrIdentifyTask(byImageUrl, v, types, result, mainCountDownLatch,this::mixedInvoiceIdentify))
                .collect(Collectors.toList());
        try {
            groupingTasksAndExecute(tasks,MIXED_LIMIT_COUNT,false);
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            log.error("concurrentMixedInvoiceIdentify InterruptedException:{0}", e);
            Thread.currentThread().interrupt();
        }
        return result;
    }

    public ConcurrentVatInvoiceVerifyResult concurrentVatInvoiceVerify(List<VatInvoiceVerify> vatInvoiceVerifies) {
        ConcurrentVatInvoiceVerifyResult result = new ConcurrentVatInvoiceVerifyResult();
        //移除参数识别失败的数据
        Iterator<VatInvoiceVerify> iterator = vatInvoiceVerifies.iterator();
        while (iterator.hasNext()){
            VatInvoiceVerify next = iterator.next();
            if (Boolean.FALSE.equals(next.notBlankParamForVerify()) || next.isTodayInvoice()) {
                result.getErrorRecords().add(VatInvoiceVerifyErrorRecord.of(next));
                iterator.remove();
            }
        }
        CountDownLatch mainCountDownLatch = new CountDownLatch(vatInvoiceVerifies.size());
        List<Runnable> vatInvoiceVerifyTasks = vatInvoiceVerifies.stream()
                .map(v -> VatInvoiceVerifyTask.createVatInvoiceVerifyTask(v, result, mainCountDownLatch,this::vatInvoiceVerify))
                .collect(Collectors.toList());
        try{
            groupingTasksAndExecute(vatInvoiceVerifyTasks,VERIFY_LIMIT_ONE_SECOND,false);
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            log.error("concurrentVatInvoiceVerify InterruptedException:{0}", e);
            Thread.currentThread().interrupt();
        }
        return result;
    }

    public ConcurrentGeneralBasicIdentifyResult concurrentGeneralBasicIdentify(boolean byImageUrl,List<String> images) {
        ConcurrentGeneralBasicIdentifyResult result = new ConcurrentGeneralBasicIdentifyResult();
        CountDownLatch mainCountDownLatch = new CountDownLatch(images.size());
        List<Runnable> tasks = images.stream()
                .map(v -> GeneralBasicIdentifyTask.createGeneralBasicIdentifyTask(byImageUrl,v, result, mainCountDownLatch, this::generalBasicIdentify))
                .collect(Collectors.toList());
        try {
            groupingTasksAndExecute(tasks,GENERAL_BASIC_LIMIT_COUNT,true);
            mainCountDownLatch.await();
        } catch (InterruptedException e) {
            log.error("concurrentGeneralBasicIdentify InterruptedException:{0}", e);
            Thread.currentThread().interrupt();
        }
        return result;
    }
    private void groupingTasksAndExecute(List<Runnable> tasks,int limit,boolean runNow) throws InterruptedException{
        if (tasks.size() > limit) {
            List<List<Runnable>> partitions = Lists.partition(tasks, limit);
            for (List<Runnable> partition : partitions) {
                if(runNow){
                    partition.forEach(EXECUTOR::submit);
                    Thread.sleep(1500);
                }else{
                    RUNNABLE_HOLDER_SCHEDULE_QUEUE.put(new RunnableHolder(partition));
                }
            }
        } else {
            if(runNow){
                tasks.forEach(EXECUTOR::submit);
            }else{
                RUNNABLE_HOLDER_SCHEDULE_QUEUE.put(new RunnableHolder(tasks));
            }
        }
    }

    /**
     * 混贴票据识别
     */
    public MixedInvoiceOCRResponse mixedInvoiceIdentify(String imageUrl, String imageBase64, List<Long> types) throws TencentCloudSDKException {
        MixedInvoiceOCRRequest req = new MixedInvoiceOCRRequest();
        if (StringUtils.isNotBlank(imageUrl)) {
            req.setImageUrl(imageUrl);
        } else if (StringUtils.isNotBlank(imageBase64)) {
            req.setImageBase64(imageBase64);
        }
        if (types != null && !types.isEmpty()) {
            req.setTypes(types.toArray(new Long[0]));
        }
        return client.MixedInvoiceOCR(req);
    }

    /**增值税核验
     * @param invoiceCode 发票代码， 一张发票一天只能查询5次。
     * @param invoiceNo   发票号码（8位）
     * @param invoiceDate 开票日期（不支持当天发票查询，只支持一年以内），如：2019-12-20。
     * @param additional  金额/发票校验码后6位（根据票种传递对应值，如果报参数错误，请仔细检查每个票种对应的值）
     *                    增值税专用发票：开具金额（不含税）
     *                    增值税普通发票、增值税电子普通发票（含通行费发票）、增值税普通发票（卷票）：校验码后6位
     *                    机动车销售统一发票：不含税价
     *                    货物运输业增值税专用发票：合计金额
     *                    二手车销售统一发票：车价合计
     * @return VatInvoiceVerifyResponse
     */
    public VatInvoiceVerifyResponse vatInvoiceVerify(String invoiceCode, String invoiceNo, String invoiceDate, String additional) throws TencentCloudSDKException {
        VatInvoiceVerifyRequest req = new VatInvoiceVerifyRequest();
        req.setInvoiceCode(invoiceCode);
        req.setInvoiceNo(invoiceNo);
        req.setInvoiceDate(invoiceDate);
        req.setAdditional(additional);
        return client.VatInvoiceVerify(req);
    }

    /**
     * 通用文字识别
     */
    public GeneralBasicOCRResponse generalBasicIdentify(String imageUrl, String imageBase64) throws TencentCloudSDKException {
        GeneralBasicOCRRequest req = new GeneralBasicOCRRequest();
        req.setIsPdf(true);
        if (StringUtils.isNotBlank(imageUrl)) {
            req.setImageUrl(imageUrl);
        } else if (StringUtils.isNotBlank(imageBase64)) {
            req.setImageBase64(imageBase64);
        }
        return client.GeneralBasicOCR(req);
    }
}
