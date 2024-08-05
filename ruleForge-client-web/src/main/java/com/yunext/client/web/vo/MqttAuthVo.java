package com.yunext.client.web.vo;


import com.yunext.common.utils.StringUtil;

import java.io.Serializable;

/**
 * @author panhui
 * @date 2023/5/19
 * @apiNote
 */
public class MqttAuthVo implements Serializable {
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
    private String password;

    public boolean isAnyEmpty() {
        return StringUtil.isAnyEmpty(this.clientid, this.username, this.password);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
