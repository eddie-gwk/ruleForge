package com.yunext.core.components.common;

import com.alibaba.fastjson2.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.api.dto.DebugMessageDto;
import com.yunext.api.dto.WebSocketMessageDto;
import com.yunext.api.service.web.WebSocketService;
import com.yunext.common.node.common.DebugNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;
import com.yunext.core.mqtt.debug.MqttApi;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;

import java.util.Date;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:47
 */
@LiteflowComponent("debug")
public class DebugCmp extends IsolationNodeComponent {

    @Resource
    private MqttApi mqttApi;

    @Override
    public void process(SubContext subContext) throws Exception {
        DebugNode.DebugProp prop = this.getProp(subContext.getRuleIndex(), DebugNode.DebugProp.class);
        DebugMessageDto<?> debugMessageDto = prop.isAll() ? new DebugMessageDto<>(prop.getNodeId(), prop.getNodeName(), new Date(), subContext.getMsg())
                : new DebugMessageDto<>(prop.getNodeId(), prop.getNodeName(), new Date(), subContext.getMsg().get(prop.getPropName()));
        String topic = "/ruleForge/" + this.getChainId() + "/" + prop.getNodeId();
        mqttApi.sendMqttMsg(topic, JSONObject.toJSONString(debugMessageDto));
    }

}
