package com.yunext.core.provider;

import com.yunext.api.dto.ResultDto;
import com.yunext.api.listener.MqttV3MessageListener;
import com.yunext.api.listener.MqttV5MessageListener;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.service.core.ComponentService;
import com.yunext.api.service.core.MqttV3Service;
import com.yunext.api.service.core.MqttV5Service;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.MqttProtocolEnum;
import com.yunext.common.node.MainContextDto;
import com.yunext.common.node.common.InjectNode;
import com.yunext.common.node.network.MqttInNode;
import jakarta.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 9:47
 */
@DubboService
public class ComponentServiceImpl implements ComponentService {

    @Resource
    private ChainExecuteService chainExecuteService;

    @Resource
    private MqttV3Service mqttV3Service;

    @Resource
    private MqttV5Service mqttV5Service;

    private final static Logger log = LoggerFactory.getLogger(ComponentServiceImpl.class);

    @Override
    public ResultDto<?> inject(BasicNode<?, ?> root) {
        if (root == null) {
            return ResultDto.fail("input root node can not be null");
        }
        InjectNode injectNode = InjectNode.create(root);
        MainContextDto mainContextDto = injectNode.propsConversion();
        for (InjectNode.InjectRule rule : injectNode.getRules()) {
            Boolean once = rule.getOnce().orElse(false);
            Double onceDelay = rule.getOnceDelay().orElse(0d);
            boolean needRepeat = rule.getRepeat().orElse(null) != null;
            Long repeatInterval = needRepeat ? rule.getRepeat().orElse(0L) : 0L;
            chainExecuteService.executeRepeatedly(root.getChainId(),
                    mainContextDto,
                    once ? onceDelay.longValue() : repeatInterval,
                    repeatInterval, needRepeat);
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> injectOnce(BasicNode<?, ?> root) {
        if (root == null) {
            return ResultDto.fail("input root node can not be null");
        }
        InjectNode injectNode = InjectNode.create(root);
        MainContextDto mainContextDto = injectNode.propsConversion();
        try {
            chainExecuteService.executeOnceSync(root.getChainId(), mainContextDto);
        } catch (Throwable e) {
            log.warn("inject once error:", e);
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> mqttIn(BasicNode<?, ?> root) {
        if (root == null) {
            return ResultDto.fail("input root node can not be null");
        }
        MqttInNode mqttInNode = MqttInNode.create(root);
        if (CollectionUtils.isNotEmpty(mqttInNode.getProps())) {
            MqttInNode.MqttInProp mqttInProp = mqttInNode.getProps().get(0);
            switch (MqttProtocolEnum.valueOf(mqttInProp.getProtocolType())) {
                case v3 -> mqttV3Service.subscribe(mqttInProp.getBrokerId(), mqttInProp.getTopic(), mqttInProp.getQos(), new MqttV3MessageListener(root.getChainId()));
                case v5 -> mqttV5Service.subscribe(mqttInProp.getBrokerId(), mqttInProp.getTopic(), mqttInProp.getQos(), new MqttV5MessageListener(root.getChainId()));
            }
        }
        return ResultDto.success();
    }
}
