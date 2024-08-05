package com.yunext.client.web.vo;


import java.io.Serializable;

public class MqttAuthResultVo implements Serializable {
    public static MqttAuthResultVo DENY_VO = new MqttAuthResultVo("deny");
    public static MqttAuthResultVo ALLOW_VO = new MqttAuthResultVo("allow");
    /**
     * allow | deny | ignore
     */
    private String result;
    /**
     * username，不要自动生成，下划线字段为EMQ需要
     */
    private boolean is_supseruser = false;

    public MqttAuthResultVo(String result) {
        this.result = result;
    }

    public MqttAuthResultVo() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isIs_supseruser() {
        return is_supseruser;
    }

    public void setIs_supseruser(boolean is_supseruser) {
        this.is_supseruser = is_supseruser;
    }
}
