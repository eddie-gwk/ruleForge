package com.yunext.core.mqtt.factory;

import com.yunext.api.dto.MqttV5BrokerConfigDto;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.packet.UserProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 16:03
 */
public class MqttV5Factory {

    private final static Logger log = LoggerFactory.getLogger(MqttV5Factory.class);

    public static MqttAsyncClient createClient(MqttV5BrokerConfigDto v5Config) {
        try {
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(v5Config.getServerUrl(), v5Config.getClientId());
            MqttConnectionOptions options = new MqttConnectionOptions();
            options.setCleanStart(v5Config.isCleanStart());
            options.setSessionExpiryInterval(v5Config.getSessionExpireInterval());
            options.setUserProperties(cover(v5Config.getUserAttribute()));
            options.setAutomaticReconnect(v5Config.isAutoRetryConnect());
            //鉴权相关
            options.setUserName(v5Config.getUserName());
            options.setPassword(v5Config.getPassword().getBytes(StandardCharsets.UTF_8));
            if (v5Config.isAutoConnect()) {
                mqttAsyncClient.connect(options);
            }
            return mqttAsyncClient;
        } catch (MqttException e) {
            log.error("create mqtt v5 client error, ", e);
        }

        return null;
    }

    public static List<UserProperty> cover(Map<String, Object> map) {
        List<UserProperty> userPropertyList = new ArrayList<>();
        if (map == null || map.isEmpty()) {
            return userPropertyList;
        }
        map.forEach((k, v) -> userPropertyList.add(new UserProperty(k, String.valueOf(v))));
        return userPropertyList;
    }
}
