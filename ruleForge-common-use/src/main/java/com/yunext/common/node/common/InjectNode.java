package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.DataTypeEnum;
import com.yunext.common.node.MainContextDto;
import com.yunext.common.node.Rule;
import com.yunext.common.utils.ModelMapperUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 18:00
 */
public class InjectNode extends BasicNode<InjectNode.InjectProp, InjectNode.InjectRule> implements Serializable {

    public InjectNode() {}

    public static InjectNode create(BasicNode<?, ?> basicNode) {
        return basicNode != null ? ModelMapperUtil.map(basicNode, InjectNode.class) : null;
    }

    public MainContextDto propsConversion() {
        MainContextDto mainContextDto = new MainContextDto();
        mainContextDto.setTimestamp(new Date());
        List<InjectProp> injectProps = Optional.ofNullable(this.getProps()).orElse(new ArrayList<>());
        Map<Object, Object> msg = new HashMap<>();
        for (InjectProp injectProp : injectProps) {
            if (injectProp.getDataType() != null) {
                injectProp.getDataType().conversion(injectProp.getName(), injectProp.getValue(), msg);
            }
        }
        mainContextDto.setMsg(msg);
        return mainContextDto;
    }

    public static class InjectProp {
        /**
         * 名称
         */
        private String name;
        /**
         * 值
         */
        private Object value;
        /**
         * 类型
         * @see com.yunext.common.enums.DataTypeEnum
         */
        private DataTypeEnum dataType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public DataTypeEnum getDataType() {
            return dataType;
        }

        public void setDataType(DataTypeEnum dataType) {
            this.dataType = dataType;
        }
    }

    public static class InjectRule {
        /**
         * 立刻执行
         */
        private Boolean once;
        /**
         * 立刻执行延迟时间，单位秒
         */
        private Double onceDelay;
        /**
         * 周期重复，单位秒
         */
        private Long repeat;

        public Optional<Boolean> getOnce() {
            return Optional.ofNullable(once);
        }

        public void setOnce(Boolean once) {
            this.once = once;
        }

        public Optional<Double> getOnceDelay() {
            return Optional.ofNullable(onceDelay);
        }

        public void setOnceDelay(Double onceDelay) {
            this.onceDelay = onceDelay;
        }

        public Optional<Long> getRepeat() {
            return Optional.ofNullable(repeat);
        }

        public void setRepeat(Long repeat) {
            this.repeat = repeat;
        }
    }

}
