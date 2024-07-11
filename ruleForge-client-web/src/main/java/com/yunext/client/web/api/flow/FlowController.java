package com.yunext.client.web.api.flow;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.service.core.ComponentService;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.api.service.reload.RuleReloadService;
import com.yunext.api.vo.ApiResultVo;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.NodeTypeEnum;
import com.yunext.common.node.MainContextDto;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 9:16
 */
@RestController
@RequestMapping(value = "/api/web/flow")
public class FlowController {

    @DubboReference
    private ChainNodeService chainNodeService;

    @DubboReference
    private RuleReloadService ruleReloadService;

    @DubboReference
    private ComponentService componentService;

    @DubboReference
    private ChainExecuteService chainExecuteService;

    @PostMapping
    public ApiResultVo<?> flow(@RequestBody List<BasicNode<?, ?>> basicNodeList) {
        List<RuleSyntaxTreeDto> syntaxTreeDtoList = chainNodeService.createChain(basicNodeList).getData();
        syntaxTreeDtoList.forEach(syntaxTree -> {
            ruleReloadService.reload(syntaxTree.getChain(), syntaxTree.getRuleDsl());
            BasicNode<?, ?> root = syntaxTree.getRoot();
            //输出节点的执行逻辑需要单独执行
            if (NodeTypeEnum.output.equals(root.getType())) {
                switch (root.getComponent()) {
                    case inject -> componentService.inject(root);
                }
            }
        });
        return ApiResultVo.success();
    }

    /**
     * 手动注入
     * @param chain
     * @param mainContextDto
     * @return
     */
    @PostMapping(value = "/inject")
    public ApiResultVo<?> inject(@RequestParam String chain, @RequestBody MainContextDto mainContextDto) {
        ResultDto<?> resultDto = chainExecuteService.executeOnceSync(chain, mainContextDto);
        return ApiResultVo.success();
    }
}
