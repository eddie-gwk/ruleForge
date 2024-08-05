package com.yunext.core.mqtt.debug;

import com.yunext.common.utils.StringUtil;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Autowired
    private Mqtt mqtt;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        connOpts.setUserName(mqtt.getUserName());
        connOpts.setPassword(mqtt.getPassword().toCharArray());
        connOpts.setConnectionTimeout(10);
        connOpts.setKeepAliveInterval(30);
        connOpts.setAutomaticReconnect(true);
        connOpts.setMqttVersion(4);
        connOpts.setServerURIs(new String[]{mqtt.getServer()});
        factory.setConnectionOptions(connOpts);
        return factory;
    }

    //发送消息到broker的handler
    @Bean
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("SER:" + StringUtil.uuid(), mqttClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }
}
