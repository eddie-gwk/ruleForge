package com.yunext.core.context;

import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:06
 */
public class SubContext {

    private String id;
    private String topic;
    private Map<Object, Object> payload;

    public SubContext copy() {
        SubContext subContext = new SubContext();
        subContext.setId(this.id);
        subContext.setTopic(this.topic);
        subContext.setPayload(this.payload);
        return subContext;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<Object, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<Object, Object> payload) {
        this.payload = payload;
    }
}
