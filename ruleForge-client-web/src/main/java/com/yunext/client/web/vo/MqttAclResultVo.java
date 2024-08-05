package com.yunext.client.web.vo;


import java.io.Serializable;


public class MqttAclResultVo implements Serializable {
    public static MqttAclResultVo DENY_VO = new MqttAclResultVo("deny");
    public static MqttAclResultVo ALLOW_VO = new MqttAclResultVo("allow");
    public static MqttAclResultVo IGNORE_VO = new MqttAclResultVo("ignore");
    /**
     * allow | deny | ignore
     */
    private String result;

    public MqttAclResultVo(String result) {
        this.result = result;
    }

    public MqttAclResultVo() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
