package com.yunext.common.enums;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description：节点类型
 * @date ：Created in 2024/7/2 15:17
 */
public enum NodeTypeEnum {
    /**
     * 虚拟节点
     */
    virtual,
    /**
     * 输入节点
     */
    input,
    /**
     * 输出节点
     */
    output,
    /**
     * 普通节点
     * 输入输出节点
     */
    ordinary,
    /**
     * 选择节点
     * 类似于switch
     */
    select,
    /**
     * 次数循环组件
     */
    loop,
    /**
     * 迭代器组件
     */
    iterate,
    /**
     * 未知
     */
    unknown;

    public static boolean isSelect(String name) {
        return select.name().equals(name);
    }

    public static NodeTypeEnum getByName(String name) {
        for (NodeTypeEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return unknown;
    }
}
