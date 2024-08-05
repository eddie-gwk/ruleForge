package com.yunext.api.dto;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/12 15:52
 */
public class BasicNodeQueryDto implements Serializable {

    /**
     * 节点类型
     * @see com.yunext.common.enums.NodeTypeEnum
     */
    private String nodeType;
    /**
     * 组件名称
     * @see com.yunext.common.enums.ComponentEnum
     */
    private String componentName;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }
}
