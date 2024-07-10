package com.yunext.client.web.api.flow;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.core.ComponentService;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.api.vo.ApiResultVo;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.NodeTypeEnum;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ComponentService componentService;

    @PostMapping
    public ApiResultVo<?> flow(@RequestBody List<BasicNode<?, ?>> basicNodeList) {
        List<RuleSyntaxTreeDto> syntaxTreeDtoList = chainNodeService.createChain(basicNodeList).getData();
        syntaxTreeDtoList.forEach(syntaxTree -> {
            BasicNode<?, ?> root = syntaxTree.getRoot();
            //输出节点的执行逻辑
            if (NodeTypeEnum.output.equals(root.getType())) {
                switch (root.getComponent()) {
                    case inject -> componentService.inject(root);
                }
            }
        });
        return ApiResultVo.success();
    }
}
