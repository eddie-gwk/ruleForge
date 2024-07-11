package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.FilterCmdEnum;
import com.yunext.common.utils.ModelMapperUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:15
 */
public class FilterNode extends BasicNode<FilterNode.FilterProp, FilterNode.FilterRule> implements Serializable {

    public static class FilterProp {
        /**
         * 监控的属性名
         */
        private String propName;

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }
    }

    public static class FilterRule {
        /**
         * 筛选指令
         */
        private FilterCmdEnum cmd;
        /**
         * 数据变化范围
         */
        private Double range;

        public FilterCmdEnum getCmd() {
            return cmd;
        }

        public void setCmd(FilterCmdEnum cmd) {
            this.cmd = cmd;
        }

        public Double getRange() {
            return range;
        }

        public void setRange(Double range) {
            this.range = range;
        }

        public boolean isPass(String propName, Map<Object, Object> lastMsg, Map<Object, Object> msg) {
            Object lastValue = lastMsg.get(propName);
            Object value = msg.get(propName);
            return switch (this.cmd) {
                case wait_change -> !Objects.equals(lastValue, value);
                //关于值变化的，统一转为double进行处理
                case gt_range -> this.dataRange(value, lastValue) > this.range;
                case gte_range -> this.dataRange(value, lastValue) >= this.range;
                case block_gt_range -> !(this.dataRange(value, lastValue) > this.range);
                case block_gte_range -> !(this.dataRange(value, lastValue) >= this.range);
            };
        }

        private double dataRange(Object value, Object lastValue) {
            if (value == null || lastValue == null) {
                return -1;
            }
            if (value instanceof Number v && lastValue instanceof  Number lv) {
                Double curr = ModelMapperUtil.map(value, Double.class);
                Double last = ModelMapperUtil.map(lastValue, Double.class);
                return curr - last;
            }
            return -1;
        }
    }
}
