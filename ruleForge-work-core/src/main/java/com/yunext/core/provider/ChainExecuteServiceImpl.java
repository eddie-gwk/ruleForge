package com.yunext.core.provider;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yunext.common.node.MainContextDto;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.dto.ResultDto;
import com.yunext.common.utils.ExecutorUtil;
import com.yunext.common.utils.ModelMapperUtil;
import com.yunext.common.utils.StringUtil;
import com.yunext.core.context.MainContext;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:26
 */
@DubboService
public class ChainExecuteServiceImpl implements ChainExecuteService {

    private final Map<String, ScheduledFuture<?>> chainRepeatedlyFutures = new ConcurrentHashMap<>();

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public ResultDto<?> executeOnce(String chain) {
        return this.executeOnce(chain, null);
    }

    @Override
    public ResultDto<?> executeOnce(String chain, MainContextDto mainContextDto) {
        if (StringUtil.isEmpty(chain)) {
            return ResultDto.fail("chain id must not be null");
        }
        MainContext mainContext = new MainContext();
        if (mainContextDto != null) {
            mainContext = ModelMapperUtil.map(mainContextDto, MainContext.class);
        }
        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp(chain, "args", mainContext);
        return ResultDto.excute(liteflowResponse.isSuccess(), liteflowResponse.getMessage());
    }

    @Override
    public ResultDto<Future<?>> executeOnceSync(String chain) {
        return this.executeOnceSync(chain, null);
    }

    @Override
    public ResultDto<Future<?>> executeOnceSync(String chain,  MainContextDto mainContextDto) {
        if (StringUtil.isEmpty(chain)) {
            return ResultDto.fail("chain id must not be null");
        }
        MainContext mainContext = new MainContext();
        if (mainContextDto != null) {
            mainContext = ModelMapperUtil.map(mainContextDto, MainContext.class);
        }
        Future<LiteflowResponse> future = flowExecutor.execute2Future(chain, "args", mainContext);
        return ResultDto.success(future);
    }

    @Override
    public ResultDto<ScheduledFuture<?>> executeRepeatedly(String chain, MainContextDto mainContextDto, Long initDelay, Long interval, boolean repeat) {
        if (chainRepeatedlyFutures.containsKey(chain)) {
            //如果已经存在任务先删除
            this.cancelRepeatTask(chain);
        }
        ScheduledFuture<?> scheduledFuture = repeat ?
                ExecutorUtil.scheduleService.scheduleAtFixedRate(() -> this.executeOnce(chain, mainContextDto), initDelay, interval, TimeUnit.SECONDS)
                : ExecutorUtil.scheduleService.schedule(() -> this.executeOnce(chain, mainContextDto), initDelay, TimeUnit.SECONDS);
        chainRepeatedlyFutures.put(chain, scheduledFuture);
        return ResultDto.success(scheduledFuture);
    }

    @Override
    public ResultDto<?> cancelRepeatTask(String chain) {
        if (chainRepeatedlyFutures.containsKey(chain)) {
            ScheduledFuture<?> scheduledFuture = chainRepeatedlyFutures.get(chain);
            scheduledFuture.cancel(true);
            scheduledFuture = null;
            chainRepeatedlyFutures.remove(chain);
        }
        return ResultDto.success();
    }
}
