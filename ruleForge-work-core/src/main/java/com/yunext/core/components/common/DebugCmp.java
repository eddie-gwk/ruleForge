package com.yunext.core.components.common;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.api.dto.WebSocketMessageDto;
import com.yunext.api.service.web.WebSocketService;
import com.yunext.common.node.common.DebugNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:47
 */
@LiteflowComponent("debug")
public class DebugCmp extends IsolationNodeComponent {

    private static final String ALL = "all";

    @DubboReference
    private WebSocketService webSocketService;

    @Override
    public void process(SubContext subContext) throws Exception {
        DebugNode.DebugProp prop = this.getProp(subContext.getRuleIndex(), DebugNode.DebugProp.class);
        WebSocketMessageDto webSocketMessageDto;
        if (ALL.equals(prop.getPropName())) {
            webSocketMessageDto = new WebSocketMessageDto(prop.getNodeId(), subContext.getMsg());
        } else {
            webSocketMessageDto = new WebSocketMessageDto(prop.getNodeId(), subContext.getMsg().get(prop.getPropName()));
        }
        webSocketService.sendMessage(webSocketMessageDto);
    }

}
