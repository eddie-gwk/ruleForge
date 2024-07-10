package com.yunext.core.context;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:05
 */
public class MainContext {

    private Date timestamp;
    private Map<Object, Object> msg;

    private final Map<String, List<SubContext>> subContextMap;

    public MainContext() {
        subContextMap = new ConcurrentHashMap<>();
    }

    public SubContext copyToSubContext() {
        SubContext subContext = new SubContext();
        subContext.setTimestamp(this.timestamp);
        subContext.setRuleIndex(0);
        subContext.setMsg(this.msg);
        return subContext;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<Object, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<Object, Object> msg) {
        this.msg = msg;
    }

    public List<SubContext> getSubContext(String cmpId) {
        return Optional.ofNullable(this.subContextMap.get(cmpId)).orElse(new ArrayList<>());
    }

    public void setSubContext(String cmpId, List<SubContext> subContextList) {
        this.subContextMap.put(cmpId, subContextList);
    }
}
