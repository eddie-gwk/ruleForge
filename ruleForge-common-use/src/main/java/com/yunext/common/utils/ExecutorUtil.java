package com.yunext.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 22:56
 */
public class ExecutorUtil {

    private static final String RULE_FORGE_THREAD_NAME = "RULE_FORGE_THREAD";
    private static final AtomicInteger THREAD_NUM = new AtomicInteger();

    public static final ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(4, r->{
        String threadName = RULE_FORGE_THREAD_NAME + "_" +  THREAD_NUM.incrementAndGet();
        return new Thread(r, threadName);
    });

}
