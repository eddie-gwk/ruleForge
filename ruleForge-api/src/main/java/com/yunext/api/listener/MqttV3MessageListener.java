package com.yunext.api.listener;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.common.node.MainContextDto;
import com.yunext.common.utils.SpringBeanUtil;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/16 9:38
 */
public class MqttV3MessageListener implements IMqttMessageListener, Serializable {

    private final String chainId;

    public MqttV3MessageListener(String chainId) {
        this.chainId = chainId;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        ChainExecuteService chainExecuteService = SpringBeanUtil.getBean(ChainExecuteService.class);
        String payload = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
        MainContextDto mainContextDto = new MainContextDto();
        mainContextDto.setMsg(JSONObject.parseObject(payload, Map.class));
        chainExecuteService.executeOnceSync(chainId, mainContextDto);
    }
}
