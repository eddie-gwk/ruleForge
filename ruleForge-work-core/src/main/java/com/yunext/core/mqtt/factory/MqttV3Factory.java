package com.yunext.core.mqtt.factory;

import com.yunext.api.dto.MqttV3BrokerConfigDto;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 16:03
 */
public class MqttV3Factory {

    private final static Logger log = LoggerFactory.getLogger(MqttV3Factory.class);

    public static MqttAsyncClient createClient(MqttV3BrokerConfigDto v3Config) {
        String serverUrl = v3Config.getHost() + ":" + v3Config.getPort();
        try {
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(serverUrl, v3Config.getClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(v3Config.isCleanSession());
            options.setAutomaticReconnect(v3Config.isAutoRetryConnect());
            options.setKeepAliveInterval(v3Config.getKeepAliveDuration());
            //验权相关
            options.setUserName(v3Config.getUserName());
            options.setPassword(v3Config.getPassword().toCharArray());
            if (v3Config.isAutoConnect()) {
                IMqttToken connect = mqttAsyncClient.connect(options);
                connect.waitForCompletion();
            }
            return mqttAsyncClient;
        } catch (MqttException e) {
            log.error("create mqtt v3 client error, ", e);
        }
        return null;
    }
}
