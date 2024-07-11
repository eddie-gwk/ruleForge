package com.yunext.common.base;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
import com.yunext.common.node.Rule;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：节点基本结构
 * @date ：Created in 2024/7/2 15:14
 */
public class BasicNode<P, R> implements Serializable {
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
     * 输入属性
     */
    private List<P> props;
    /**
     * 自定义规则
     */
    private List<R> rules;
    /**
     * 选择组件的分支标签
     */
    private List<String> tags;
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
    private List<List<String>> wires;

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

    public List<P> getProps() {
        return props;
    }

    public void setProps(List<P> props) {
        this.props = props;
    }

    public List<R> getRules() {
        return rules;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setRules(List<R> rules) {
        this.rules = rules;
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

    public List<List<String>> getWires() {
        return wires;
    }

    public void setWires(List<List<String>> wires) {
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
        BasicNode<?, ?> basicNode = (BasicNode<?, ?>) obj;
        return basicNode.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
