package com.yunext.common.enums;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:20
 */
public enum FilterCmdEnum {

    /**
     * 阻塞直到值变化
     */
    wait_change,
    /**
     * 数据大于变化范围
     */
    gt_range,
    /**
     * 数据大于等于变化范围
     */
    gte_range,
    /**
     * 数据大于变化范围则阻塞
     */
    block_gt_range,
    /**
     * 数据大于等于变化范围则阻塞
     */
    block_gte_range;
}
