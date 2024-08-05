package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:51
 */
public class DebugNode extends BasicNode<DebugNode.DebugProp, DebugNode.DebugRule> implements Serializable {

    public static class DebugProp {
        /**
         * 组件id
         */
        private String nodeId;
        /**
         * 节点名称
         */
        private String nodeName;
        /**
         * 输出字段
         */
        private String propName;
        /**
         * 与调试输出相同
         */
        private Boolean all;
        /**
         * 输出位置
         * 0 调试窗口
         * 1 控制台
         */
        private List<Integer> position;

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

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public Boolean getAll() {
            return all;
        }

        public void setAll(Boolean all) {
            this.all = all;
        }

        public List<Integer> getPosition() {
            return position;
        }

        public void setPosition(List<Integer> position) {
            this.position = position;
        }

        public Boolean isAll() {
            return Optional.ofNullable(all).orElse(false);
        }
    }

    public static class DebugRule {

    }
}
