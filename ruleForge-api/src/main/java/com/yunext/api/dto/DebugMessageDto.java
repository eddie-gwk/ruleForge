package com.yunext.api.dto;


import java.io.Serializable;
import java.util.Date;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：调试节点输出
 * @date ：Created in 2024/7/11 16:44
 */

public class DebugMessageDto<T> implements Serializable {
    /**
     * 节点id
     */
    private String nodeId;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 时间戳
     */
    private Date timestamp;
    /**
     * 消息
     */
    private T payload;

    public DebugMessageDto() {
    }

    public DebugMessageDto(String nodeId, String nodeName, Date timestamp, T payload) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
