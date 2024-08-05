package com.yunext.api.po;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:38
 */
@Document(collection = "mqtt_v5_broker_config")
public class MqttV5BrokerConfig extends MqttBrokerConfig implements Serializable {
    /**
     * 新开始
     */
    private boolean cleanStart;
    /**
     * 会话过期间隔
     */
    private Long sessionExpireInterval;
    /**
     * 用户属性
     */
    private Map<String, Object> userAttribute;

    public boolean isCleanStart() {
        return cleanStart;
    }

    public void setCleanStart(boolean cleanStart) {
        this.cleanStart = cleanStart;
    }

    public Long getSessionExpireInterval() {
        return sessionExpireInterval;
    }

    public void setSessionExpireInterval(Long sessionExpireInterval) {
        this.sessionExpireInterval = sessionExpireInterval;
    }

    public Map<String, Object> getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Map<String, Object> userAttribute) {
        this.userAttribute = userAttribute;
    }
}
