package com.yunext.api.service.plant;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.common.base.BasicNode;
import com.yunext.api.dto.ResultDto;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/3 17:31
 */
public interface ChainNodeService {

    /**
     * 创建规则链
     * @param basicNodeList
     * @return
     */
    ResultDto<List<RuleSyntaxTreeDto>> createChain(List<BasicNode<?, ?>> basicNodeList);

}
