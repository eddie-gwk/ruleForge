package com.yunext.api.service.reload;

import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.common.base.ResultDto;

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
}
