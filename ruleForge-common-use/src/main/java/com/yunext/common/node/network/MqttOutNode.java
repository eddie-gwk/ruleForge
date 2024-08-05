package com.yunext.common.node.network;



import com.yunext.common.base.BasicNode;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/16 10:12
 */
public class MqttOutNode extends BasicNode<MqttOutNode.MqttOutProp, MqttOutNode.MqttOutRule> implements Serializable {

    public static class MqttOutProp {
        /**
         * 服务质量
         * @see com.yunext.common.enums.MqttQosEnum
         */
        private int qos;
        /**
         * 订阅主题
         */
        private String topic;
        /**
         * 订阅型号，目前默认单订阅
         */
        private boolean retained;
        /**
         * 协议类型 v3/v5
         */
        private String protocolType;
        /**
         * broker id
         */
        private String brokerId;

        public int getQos() {
            return qos;
        }

        public void setQos(int qos) {
            this.qos = qos;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public boolean isRetained() {
            return retained;
        }

        public void setRetained(boolean retained) {
            this.retained = retained;
        }

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getBrokerId() {
            return brokerId;
        }

        public void setBrokerId(String brokerId) {
            this.brokerId = brokerId;
        }
    }

    public static class MqttOutRule {

    }
}
