package com.yunext.api.dto;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 17:14
 */
public class WebSocketMessageDto implements Serializable {

    private String nodeId;

    private Object value;

    public WebSocketMessageDto(String nodeId, Object value) {
        this.nodeId = nodeId;
        this.value = value;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
