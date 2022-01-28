package com.tzxx.common.tencentservice.bill.concurrent;

import com.tzxx.common.exception.BusinessException;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author tzxx
 */
public class OcrRejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new BusinessException("系统繁忙，请稍后重试");
    }
}
