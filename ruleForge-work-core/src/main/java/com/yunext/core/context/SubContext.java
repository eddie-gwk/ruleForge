package com.yunext.core.context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:06
 */
public class SubContext {

    private Date timestamp;
    private int ruleIndex;
    private Map<Object, Object> msg;

    public SubContext() {
        this.timestamp = new Date();
        this.ruleIndex = 0;
        this.msg = new HashMap<>();
    }

    public SubContext copy() {
        SubContext subContext = new SubContext();
        subContext.setTimestamp(new Date());
        subContext.setRuleIndex(this.ruleIndex);
        subContext.setMsg(this.msg);
        return subContext;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public void setRuleIndex(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    public Map<Object, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<Object, Object> msg) {
        this.msg = msg;
    }
}
