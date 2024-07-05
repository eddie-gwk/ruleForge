package com.yunext.common.enums;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：组件
 * @nitice 需要与 ruleForge-work-core\src\main\java\com\yunext\components下的组件进行同步
 * @date ：Created in 2024/7/4 13:13
 */
public enum ComponentEnum {

    //仅供测试使用
    A1,B1,C1,D1,E1,F1,G1,H1,I1,
    UNDEFINED, ROOT;

    public static ComponentEnum getByName(String name) {
        for (ComponentEnum value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return UNDEFINED;
    }
}
