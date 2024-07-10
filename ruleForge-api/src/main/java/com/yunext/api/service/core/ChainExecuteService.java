package com.yunext.api.service.core;

import com.yunext.common.node.MainContextDto;
import com.yunext.api.dto.ResultDto;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:25
 */
public interface ChainExecuteService {

    /**
     * 执行一次
     * 不带上下文、同步
     * @param chain
     * @return
     */
    ResultDto<?> executeOnce(String chain);

    /**
     * 执行一次
     * 带初始上下文、同步
     * @param chain
     * @return
     */
    ResultDto<?> executeOnce(String chain, MainContextDto mainContextDto);

    ResultDto<Future<?>> executeOnceSync(String chain);

    ResultDto<Future<?>> executeOnceSync(String chain, MainContextDto mainContextDto);

    ResultDto<ScheduledFuture<?>> executeRepeatedly(String chain, MainContextDto mainContextDto,Long initDelay, Long interval, boolean repeat);

    ResultDto<?> cancelRepeatTask(String chain);
}
