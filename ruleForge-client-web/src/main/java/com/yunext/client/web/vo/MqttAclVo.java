package com.yunext.client.web.vo;


import com.yunext.common.utils.StringUtil;

import java.io.Serializable;

/**
 * @author panhui
 * @date 2023/5/19
 * @apiNote
 */
public class MqttAclVo implements Serializable {
    /**
     * clientID
     */
    private String clientid;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String topic;

    private String action;

    public boolean isAnyEmpty() {
        return StringUtil.isAnyEmpty(this.clientid, this.username, this.action, this.topic);

    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
