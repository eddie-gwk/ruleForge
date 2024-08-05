package com.yunext.api.po;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:33
 */
@Document(collection = "mqtt_v3_broker_config")
public class MqttV3BrokerConfig extends MqttBrokerConfig implements Serializable {
    /**
     * 使用新会话
     */
    private boolean cleanSession;

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }
}
