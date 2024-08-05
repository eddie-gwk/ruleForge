package com.yunext.common.enums;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：mqtt协议版本
 * @date ：Created in 2024/7/15 17:32
 */
public enum MqttProtocolEnum {
    v3,v5;

    public static boolean isV3(String name) {
        return v3.name().equals(name);
    }

    public static boolean isV5(String name) {
        return v5.name().equals(name);
    }
}
