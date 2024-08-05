package com.yunext.api.service.reload;

import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.common.base.ScriptDSL;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 13:44
 */
public interface RuleReloadService {


    public ResultDto<?> reload(String chain, String rule);

    public ResultDto<?> reload(RuleSyntaxTreeDto ruleSyntaxTreeDto);

    public ResultDto<?> reload(List<RuleSyntaxTreeDto> ruleSyntaxTreeDtoList);

    ResultDto<?> reloadScript(List<ScriptDSL> scriptDslList);
}
