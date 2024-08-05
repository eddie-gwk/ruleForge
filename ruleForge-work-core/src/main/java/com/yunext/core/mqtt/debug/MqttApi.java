package com.yunext.core.mqtt.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MqttApi {

    public static final String DEFAULT_TOPIC_PERFIX = "/snowflake/";

    public static final String TOPIC_SPLIT = "/";

    public static final String TOPIC_REQUEST = "request";

    /**
     * qos 1
     */
    public static final int QOS_1 = 1;

    @Autowired
    private MqttConfig mqttConfig;

    /**
     * 发送mqtt消息
     */
    public void sendMqttMsg(String topicName, String message) {
        Message<String> messages = MessageBuilder.withPayload(message)
                .setHeader(MqttHeaders.TOPIC, topicName)
                .setHeader(MqttHeaders.QOS, QOS_1)
                .setHeader(MqttHeaders.RETAINED, Boolean.FALSE)
                .build();
        mqttConfig.mqttOutbound().handleMessage(messages);
    }
}
