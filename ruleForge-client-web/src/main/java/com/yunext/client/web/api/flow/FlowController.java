package com.yunext.client.web.api.flow;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.BasicNodeQueryDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.service.core.ComponentService;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.api.service.reload.RuleReloadService;
import com.yunext.api.service.storage.NodeStorageService;
import com.yunext.api.vo.ApiResultVo;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
import com.yunext.common.node.MainContextDto;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @DubboReference
    private NodeStorageService nodeStorageService;

    @PostMapping
    public ApiResultVo<?> flow(@RequestBody List<BasicNode<?, ?>> basicNodeList) {
        List<String> scriptErr = new ArrayList<>();
        List<RuleSyntaxTreeDto> syntaxTreeDtoList = chainNodeService.createChain(basicNodeList).getData();
        for (RuleSyntaxTreeDto syntaxTree : syntaxTreeDtoList) {
            ruleReloadService.reload(syntaxTree.getChain(), syntaxTree.getRuleDsl());
            BasicNode<?, ?> root = syntaxTree.getRoot();
            //1.刷新脚本节点
            if (CollectionUtil.isNotEmpty(syntaxTree.getScriptDSLList())) {
                ResultDto<?> resultDto = ruleReloadService.reloadScript(syntaxTree.getScriptDSLList());
                if (!resultDto.isSuccess()) {
                    scriptErr.add(String.format("节点[%s]的脚本语法不符合规范，不会被执行.", root.getName()));
                    continue;
                }
            }
            //2.刷新规则
            ResultDto<?> reload = ruleReloadService.reload(syntaxTree.getChain(), syntaxTree.getRuleDsl());
            if (reload.isSuccess()) {
                //3.输出节点的逻辑需要单独执行
                if (NodeTypeEnum.output.equals(root.getType())) {
                    switch (root.getComponent()) {
                        case inject -> componentService.inject(root);
                        case mqtt_in -> componentService.mqttIn(root);
                    }
                }
            } else {
                scriptErr.add(String.format("规则校验失败：%s", reload.getErrMsg()));
            }
        }
        return ApiResultVo.success(scriptErr);
    }

    /**
     * 手动注入
     *
     * @param nodeId 节点
     * @return
     */
    @PostMapping(value = "/inject")
    public ApiResultVo<?> inject(@RequestParam String nodeId) {
        BasicNode<?, ?> node = nodeStorageService.findOne(nodeId);
        if (node == null) {
            return ApiResultVo.fail("节点不存在");
        }
        if (!ComponentEnum.isInject(node.getComponent().name())) {
            return ApiResultVo.fail("只允许inject节点进行注入");
        }
        ResultDto<?> resultDto = componentService.injectOnce(node);
        return ApiResultVo.result(resultDto);
    }

    /**
     * 查询所有节点
     * @param queryDto
     * @return
     */
    @GetMapping(value = "/find/all/node")
    public ApiResultVo<List<BasicNode<?, ?>>> findAllNode(BasicNodeQueryDto queryDto) {
        List<BasicNode<?, ?>> list = nodeStorageService.list(queryDto);
        return ApiResultVo.success(list);
    }
}
