package com.yunext.core.provider;

import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.script.validator.ScriptValidator;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.reload.RuleReloadService;
import com.yunext.api.dto.ResultDto;
import com.yunext.common.base.ScriptDSL;
import com.yunext.common.utils.StringUtil;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${liteflow.rule-source-ext-data-map.chainKey}")
    private String chainKey;

    @Value("${liteflow.rule-source-ext-data-map.scriptKey}")
    private String scriptKey;

    private final static Logger log = LoggerFactory.getLogger(RuleReloadServiceImpl.class);

    @Override
    public ResultDto<?> reload(String chain, String rule) {
        if (StringUtil.isEmpty(chain)) {
            return ResultDto.fail("must specify the chain of rules to be reload");
        }
        RMapCache<Object, Object> chainRule = redissonClient.getMapCache("chain_rules");
        //验证DSL是否合法,不合法直接删除，不让执行
        boolean validate = LiteFlowChainELBuilder.validate(StringUtil.removeWhitespace(rule));
        if (validate) {
            chainRule.put(chain, rule);
        } else {
            chainRule.remove(chain);
            return ResultDto.fail(String.format("syntax error, chain [%s] will not execute.", chain));
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

    @Override
    public ResultDto<?> reloadScript(List<ScriptDSL> scriptDslList) {
        RMapCache<Object, Object> chainScripts = redissonClient.getMapCache(scriptKey);
        for (ScriptDSL scriptDsl : scriptDslList) {
            boolean validate = ScriptValidator.validate(scriptDsl.getScript());
            if (!validate) {
                log.error(String.format("script validate failed, key:%s, script:\n%s", scriptDsl.getKey(), scriptDsl.getScript()));
                return ResultDto.fail("js 脚本校验失败");
            }
            chainScripts.put(scriptDsl.getKey(), scriptDsl.getScript());
        }
        return ResultDto.success();
    }
}
