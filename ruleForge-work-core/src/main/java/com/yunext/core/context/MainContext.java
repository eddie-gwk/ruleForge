package com.yunext.core.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:05
 */
public class MainContext {

    private String id;
    private String topic;
    private Map<Object, Object> payload;

    private final List<SubContext> subContexts;

    public MainContext() {
        subContexts = Collections.synchronizedList(new ArrayList<>());
    }

    public SubContext copyToSub() {
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

    public List<SubContext> getSubContexts() {
        return subContexts;
    }
}
