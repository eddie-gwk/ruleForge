package com.yunext.common.base;

import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：基本节点
 * @date ：Created in 2024/7/2 15:14
 */
public class BasicNode implements Serializable {
    /**
     * 节点id
     */
    private String id;
    /**
     * 节点类型
     * @see NodeTypeEnum
     */
    private NodeTypeEnum type;
    /**
     * 组件
     * @see ComponentEnum
     */
    private ComponentEnum component;
    /**
     * 输出口
     */
    private Map<String, Object>[] outputs;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 规则链id
     */
    private String chainId;
    /**
     * 导线输出节点
     * 数组内容是节点id
     * 如果包含自己的id,通常会报错
     */
    private String[][] wires;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NodeTypeEnum getType() {
        return type;
    }

    public void setType(NodeTypeEnum type) {
        this.type = type;
    }

    public ComponentEnum getComponent() {
        return component;
    }

    public void setComponent(ComponentEnum component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public Map<String, Object>[] getOutputs() {
        return outputs;
    }

    public void setOutputs(Map<String, Object>[] outputs) {
        this.outputs = outputs;
    }

    public String[][] getWires() {
        return wires;
    }

    public void setWires(String[][] wires) {
        this.wires = wires;
    }

    @Override
    public String toString() {
        return String.format("[node:id=%s]", this.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicNode basicNode = (BasicNode) obj;
        return basicNode.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
