package com.yunext.api.po;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:36
 */
public class MqttBrokerConfig implements Serializable {
    /**
     * id
     */
    @Id
    private String id;
    /**
     * broker id
     */
    private String brokerId;
    /**
     * 名称
     */
    private String name;
    /**
     * 端口 默认1883
     */
    private Integer port;
    /**
     * 服务器地址
     */
    private String host;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否自动连接
     */
    private boolean autoConnect;
    /**
     * 是否自动重连
     */
    private boolean autoRetryConnect;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 存活时长(单位s)
     */
    private Integer keepAliveDuration;

    public String getServerUrl() {
        return this.getHost() + ":" + this.getPort();
    }

    public Integer getPort() {
        return Optional.ofNullable(port).orElse(1883);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutoConnect() {
        return autoConnect;
    }

    public void setAutoConnect(boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

    public boolean isAutoRetryConnect() {
        return autoRetryConnect;
    }

    public void setAutoRetryConnect(boolean autoRetryConnect) {
        this.autoRetryConnect = autoRetryConnect;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(Integer keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }
}
