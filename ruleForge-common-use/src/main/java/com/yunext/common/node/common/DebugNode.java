package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;

import java.io.Serializable;

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
         * 输出字段
         * all 表示输出全部
         */
        private String propName;

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }
    }

    public static class DebugRule {

    }
}
