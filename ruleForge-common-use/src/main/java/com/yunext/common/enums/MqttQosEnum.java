package com.yunext.common.enums;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 16:14
 */
public enum MqttQosEnum {
    /**
     * 只有一次
     */
    qos_0 (0),
    /**
     * 至少一次
     */
    qos_1 (1),
    /**
     * 至多一次
     */
    qos_2 (2);

    public final int qos;

    MqttQosEnum(int qos) {
        this.qos = qos;
    }
}
