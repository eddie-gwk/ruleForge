package com.yunext.core.components.network;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.api.service.core.MqttV3Service;
import com.yunext.api.service.core.MqttV5Service;
import com.yunext.common.enums.MqttProtocolEnum;
import com.yunext.common.node.network.MqttOutNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/16 10:11
 */
@LiteflowComponent("mqtt_out")
public class MqttOutCmp extends IsolationNodeComponent {

    @DubboReference
    private MqttV3Service mqttV3Service;

    @DubboReference
    private MqttV5Service mqttV5Service;

    @Override
    public void process(SubContext subContext) throws Exception {
        MqttOutNode.MqttOutProp prop = this.getProp(subContext.getRuleIndex(), MqttOutNode.MqttOutProp.class);
        switch (MqttProtocolEnum.valueOf(prop.getProtocolType())) {
            case v3 -> mqttV3Service.publish(prop.getBrokerId(), prop.getTopic(), prop.getQos(), prop.isRetained(), subContext.getMsg());
            case v5 -> mqttV5Service.publish(prop.getBrokerId(), prop.getTopic(), prop.getQos(), prop.isRetained(), subContext.getMsg());
        }
    }

}
