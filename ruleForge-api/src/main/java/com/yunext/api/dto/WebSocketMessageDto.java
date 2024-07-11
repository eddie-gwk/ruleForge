package com.yunext.api.dto;

import org.springframework.web.socket.WebSocketMessage;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 17:14
 */
public class WebSocketMessageDto<T> implements WebSocketMessage<T>, Serializable {

    private String nodeId;

    private T payload;

    @Override
    public T getPayload() {
        return payload;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    public WebSocketMessageDto(String nodeId, T payload) {
        this.nodeId = nodeId;
        this.payload = payload;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
