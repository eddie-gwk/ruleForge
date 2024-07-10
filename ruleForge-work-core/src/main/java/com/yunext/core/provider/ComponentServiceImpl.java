package com.yunext.core.provider;

import com.yunext.api.dto.ResultDto;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.service.core.ComponentService;
import com.yunext.common.base.BasicNode;
import com.yunext.common.node.MainContextDto;
import com.yunext.common.node.common.InjectNode;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 9:47
 */
@DubboService
public class ComponentServiceImpl implements ComponentService {

    @Resource
    private ChainExecuteService chainExecuteService;

    @Override
    public ResultDto<?> inject(BasicNode<?, ?> root) {
        if (root == null) {
            return ResultDto.fail("input root node can not be null");
        }
        InjectNode injectNode = InjectNode.create(root);
        MainContextDto mainContextDto = injectNode.propsConversion();
        for (InjectNode.InjectRule rule : injectNode.getRules()) {
            Boolean once = rule.getOnce().orElse(false);
            Double onceDelay = rule.getOnceDelay().orElse(0d);
            boolean needRepeat = rule.getRepeat().orElse(null) != null;
            Long repeatInterval = needRepeat ? rule.getRepeat().orElse(0L) : 0L;
            chainExecuteService.executeRepeatedly(root.getChainId(),
                    mainContextDto,
                    once ? onceDelay.longValue() : repeatInterval,
                    repeatInterval, needRepeat);
        }
        return ResultDto.success();
    }
}
