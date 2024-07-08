package com.yunext.core.provider;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.common.base.ResultDto;
import com.yunext.common.utils.StringUtil;
import com.yunext.core.context.MainContext;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;


/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:26
 */
@DubboService
public class ChainExecuteServiceImpl implements ChainExecuteService {

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public ResultDto<?> executeOnce(String chain) {
        if (StringUtil.isNotEmpty(chain)) {
            flowExecutor.execute2Resp(chain, "args", MainContext.class);
        }
        return ResultDto.success();
    }
}
