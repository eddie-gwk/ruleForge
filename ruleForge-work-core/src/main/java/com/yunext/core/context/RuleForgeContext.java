package com.yunext.core.context;

import com.yunext.common.utils.StringUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：规则链上下文
 * @date ：Created in 2024/7/5 13:49
 */
public class RuleForgeContext implements Serializable {

    public RuleForgeContext() {
        this.id = StringUtil.uuid();
        this.timestamp = new Date();
        this.payload = new ConcurrentHashMap<>();
    }

    private final String id;

    private String topic;

    private Date timestamp;

    private final ConcurrentMap<Object, Object> payload;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ConcurrentMap<Object, Object> getPayload() {
        return payload;
    }
}
