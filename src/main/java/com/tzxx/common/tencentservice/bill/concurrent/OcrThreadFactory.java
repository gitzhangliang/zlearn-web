package com.tzxx.common.tencentservice.bill.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tzxx
 */
public class OcrThreadFactory implements ThreadFactory {
    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    public OcrThreadFactory(String whatFeatureOfGroup) {
        namePrefix = "OcrThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = namePrefix + nextId.getAndIncrement();
        return new Thread(null, runnable, name, 0);
    }
}