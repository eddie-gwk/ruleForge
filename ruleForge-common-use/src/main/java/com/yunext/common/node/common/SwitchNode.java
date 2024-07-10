package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.CompareCmdEnum;
import com.yunext.common.enums.DataTypeEnum;
import com.yunext.common.utils.ModelMapperUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 15:02
 */
public class SwitchNode extends BasicNode<SwitchNode.SwitchProp, SwitchNode.SwitchRule> implements Serializable {

    public static class SwitchProp {
        /**
         * 参数名
         */
        private String propName;
        /**
         * 参数目标
         * msg/flow/global
         */
        private String propTarget;

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public String getPropTarget() {
            return propTarget;
        }

        public void setPropTarget(String propTarget) {
            this.propTarget = propTarget;
        }
    }

    public static class SwitchRule {
        /**
         * 比较值
         */
        private Object value;
        /**
         * 比较值类型，目前仅支持数字、字符、布尔
         */
        private DataTypeEnum dataType;
        /**
         * 比较命令
         */
        private CompareCmdEnum compareCmd;

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

        public CompareCmdEnum getCompareCmd() {
            return compareCmd;
        }

        public void setCompareCmd(CompareCmdEnum compareCmd) {
            this.compareCmd = compareCmd;
        }

        public boolean isPass(Object msgValue) {
            return switch (this.compareCmd) {
                case eq -> this.isEq(msgValue);
                case lt -> this.isLt(msgValue);
                case lte -> this.isLte(msgValue);
                case gt -> this.isGt(msgValue);
                case gte -> this.isGte(msgValue);
                case ne -> this.isNt(msgValue);
                case is_true -> Boolean.TRUE.equals(msgValue);
                case is_false -> Boolean.FALSE.equals(msgValue);
                case is_null -> msgValue != null;
                case not_null -> msgValue == null;
                case type_of -> dataType.valueTypeof(msgValue);
            };
        }

        private boolean isEq(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> ModelMapperUtil.map(msgValue, Boolean.class).equals(ModelMapperUtil.map(this.value, Boolean.class));
                case TEXT_TYPE -> ModelMapperUtil.map(msgValue, String.class).equals(ModelMapperUtil.map(this.value, String.class));
                case INTEGER_TYPE -> ModelMapperUtil.map(msgValue, Integer.class).equals(ModelMapperUtil.map(this.value, Integer.class));
                case LONG_TYPE -> ModelMapperUtil.map(msgValue, Long.class).equals(ModelMapperUtil.map(this.value, Long.class));
                case FLOAT_TYPE -> ModelMapperUtil.map(msgValue, Float.class).equals(ModelMapperUtil.map(this.value, Float.class));
                case DOUBLE_TYPE -> ModelMapperUtil.map(msgValue, Double.class).equals(ModelMapperUtil.map(this.value, Double.class));
                case DATE_TYPE -> ModelMapperUtil.map(msgValue, Date.class).equals(ModelMapperUtil.map(this.value, Date.class));
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }

        private boolean isLt(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> ModelMapperUtil.map(msgValue, Boolean.class).compareTo(ModelMapperUtil.map(this.value, Boolean.class)) < 0;
                case TEXT_TYPE -> ModelMapperUtil.map(msgValue, String.class).compareTo(ModelMapperUtil.map(this.value, String.class)) < 0;
                case INTEGER_TYPE -> ModelMapperUtil.map(msgValue, Integer.class).compareTo(ModelMapperUtil.map(this.value, Integer.class)) < 0;
                case LONG_TYPE -> ModelMapperUtil.map(msgValue, Long.class).compareTo(ModelMapperUtil.map(this.value, Long.class)) < 0;
                case FLOAT_TYPE -> ModelMapperUtil.map(msgValue, Float.class).compareTo(ModelMapperUtil.map(this.value, Float.class)) < 0;
                case DOUBLE_TYPE -> ModelMapperUtil.map(msgValue, Double.class).compareTo(ModelMapperUtil.map(this.value, Double.class)) < 0;
                case DATE_TYPE -> ModelMapperUtil.map(msgValue, Date.class).compareTo(ModelMapperUtil.map(this.value, Date.class)) < 0;
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }

        private boolean isLte(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> ModelMapperUtil.map(msgValue, Boolean.class).compareTo(ModelMapperUtil.map(this.value, Boolean.class)) <= 0;
                case TEXT_TYPE -> ModelMapperUtil.map(msgValue, String.class).compareTo(ModelMapperUtil.map(this.value, String.class)) <= 0;
                case INTEGER_TYPE -> ModelMapperUtil.map(msgValue, Integer.class).compareTo(ModelMapperUtil.map(this.value, Integer.class)) <= 0;
                case LONG_TYPE -> ModelMapperUtil.map(msgValue, Long.class).compareTo(ModelMapperUtil.map(this.value, Long.class)) <= 0;
                case FLOAT_TYPE -> ModelMapperUtil.map(msgValue, Float.class).compareTo(ModelMapperUtil.map(this.value, Float.class)) <= 0;
                case DOUBLE_TYPE -> ModelMapperUtil.map(msgValue, Double.class).compareTo(ModelMapperUtil.map(this.value, Double.class)) <= 0;
                case DATE_TYPE -> ModelMapperUtil.map(msgValue, Date.class).compareTo(ModelMapperUtil.map(this.value, Date.class)) <= 0;
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }

        private boolean isGt(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> ModelMapperUtil.map(msgValue, Boolean.class).compareTo(ModelMapperUtil.map(this.value, Boolean.class)) > 0;
                case TEXT_TYPE -> ModelMapperUtil.map(msgValue, String.class).compareTo(ModelMapperUtil.map(this.value, String.class)) > 0;
                case INTEGER_TYPE -> ModelMapperUtil.map(msgValue, Integer.class).compareTo(ModelMapperUtil.map(this.value, Integer.class)) > 0;
                case LONG_TYPE -> ModelMapperUtil.map(msgValue, Long.class).compareTo(ModelMapperUtil.map(this.value, Long.class)) > 0;
                case FLOAT_TYPE -> ModelMapperUtil.map(msgValue, Float.class).compareTo(ModelMapperUtil.map(this.value, Float.class)) > 0;
                case DOUBLE_TYPE -> ModelMapperUtil.map(msgValue, Double.class).compareTo(ModelMapperUtil.map(this.value, Double.class)) > 0;
                case DATE_TYPE -> ModelMapperUtil.map(msgValue, Date.class).compareTo(ModelMapperUtil.map(this.value, Date.class)) > 0;
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }

        private boolean isGte(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> ModelMapperUtil.map(msgValue, Boolean.class).compareTo(ModelMapperUtil.map(this.value, Boolean.class)) >= 0;
                case TEXT_TYPE -> ModelMapperUtil.map(msgValue, String.class).compareTo(ModelMapperUtil.map(this.value, String.class)) >= 0;
                case INTEGER_TYPE -> ModelMapperUtil.map(msgValue, Integer.class).compareTo(ModelMapperUtil.map(this.value, Integer.class)) >= 0;
                case LONG_TYPE -> ModelMapperUtil.map(msgValue, Long.class).compareTo(ModelMapperUtil.map(this.value, Long.class)) >= 0;
                case FLOAT_TYPE -> ModelMapperUtil.map(msgValue, Float.class).compareTo(ModelMapperUtil.map(this.value, Float.class)) >= 0;
                case DOUBLE_TYPE -> ModelMapperUtil.map(msgValue, Double.class).compareTo(ModelMapperUtil.map(this.value, Double.class)) >= 0;
                case DATE_TYPE -> ModelMapperUtil.map(msgValue, Date.class).compareTo(ModelMapperUtil.map(this.value, Date.class)) >= 0;
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }

        private boolean isNt(Object msgValue) {
            return switch (this.dataType) {
                case BOOL_TYPE -> !ModelMapperUtil.map(msgValue, Boolean.class).equals(ModelMapperUtil.map(this.value, Boolean.class));
                case TEXT_TYPE -> !ModelMapperUtil.map(msgValue, String.class).equals(ModelMapperUtil.map(this.value, String.class));
                case INTEGER_TYPE -> !ModelMapperUtil.map(msgValue, Integer.class).equals(ModelMapperUtil.map(this.value, Integer.class));
                case LONG_TYPE -> !ModelMapperUtil.map(msgValue, Long.class).equals(ModelMapperUtil.map(this.value, Long.class));
                case FLOAT_TYPE -> !ModelMapperUtil.map(msgValue, Float.class).equals(ModelMapperUtil.map(this.value, Float.class));
                case DOUBLE_TYPE -> !ModelMapperUtil.map(msgValue, Double.class).equals(ModelMapperUtil.map(this.value, Double.class));
                case DATE_TYPE -> !ModelMapperUtil.map(msgValue, Date.class).equals(ModelMapperUtil.map(this.value, Date.class));
                case MSG_TYPE ,ENUM_TYPE, STRUCT_TYPE ,ARRAY_TYPE, UNKNOWN_TYPE-> false;
            };
        }


    }
}
