package com.yunext.core.provider;

import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.reload.RuleReloadService;
import com.yunext.common.base.ResultDto;
import com.yunext.common.utils.StringUtil;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:28
 */
@DubboService
public class RuleReloadServiceImpl implements RuleReloadService {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public ResultDto<?> reload(String chain, String rule) {
        if (StringUtil.isEmpty(chain)) {
            return ResultDto.fail("must specify the chain of rules to be reload");
        }
        RMapCache<Object, Object> chainRule = redissonClient.getMapCache("chain_rules");
        //验证DSL是否合法,不合法直接删除，不让执行
        boolean validate = LiteFlowChainELBuilder.validate(rule);
        if (validate) {
            chainRule.put(chain, rule);
        } else {
            chainRule.remove(chain);
        }

        return ResultDto.success();
    }

    @Override
    public ResultDto<?> reload(RuleSyntaxTreeDto ruleSyntaxTreeDto) {
        if (ruleSyntaxTreeDto == null) {
            return ResultDto.success("syntax tree is null, pass reload");
        }
        return reload(ruleSyntaxTreeDto.getChain(), ruleSyntaxTreeDto.getRuleDsl());
    }

    @Override
    public ResultDto<?> reload(List<RuleSyntaxTreeDto> ruleSyntaxTreeDtoList) {
        if (ruleSyntaxTreeDtoList == null || ruleSyntaxTreeDtoList.isEmpty()) {
            return ResultDto.success("syntax tree list is empty, pass reload");
        }
        for (RuleSyntaxTreeDto ruleSyntaxTreeDto : ruleSyntaxTreeDtoList) {
            reload(ruleSyntaxTreeDto);
        }
        return ResultDto.success();
    }
}
