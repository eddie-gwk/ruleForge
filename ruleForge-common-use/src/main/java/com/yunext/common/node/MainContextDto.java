package com.yunext.common.node;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 22:38
 */
public class MainContextDto implements Serializable {

    private Date timestamp;
    private Map<Object, Object> msg;

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
}
