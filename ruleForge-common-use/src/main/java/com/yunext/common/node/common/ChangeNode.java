package com.yunext.common.node.common;

import com.yunext.common.base.BasicNode;
import com.yunext.common.base.ReplaceProp;
import com.yunext.common.enums.DataTypeEnum;
import com.yunext.common.utils.ModelMapperUtil;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 13:09
 */
public class ChangeNode extends BasicNode<ChangeNode.ChangeProp, ChangeNode.ChangeRule> implements Serializable {

    public static class ChangeProp {
    }

    public static class ChangeRule {
        /**
         * 参数名
         */
        private String propName;
        /**
         * 参数所在位置
         * msg/global/flow
         */
        private String propTarget;
        /**
         * 操作
         * set/replace/delete
         */
        private ChangeCommandEnum command;
        /**
         * 要替换的值
         */
        private Object regxValue;
        /**
         * 替换值类型
         */
        private DataTypeEnum regxDataType;
        /**
         * 值
         */
        private Object value;
        /**
         * 值类型
         */
        private DataTypeEnum dataType;

        public void change(Map<Object, Object> msg) {
            switch (command) {
                case set -> dataType.conversion(propName, value, msg);
                case replace -> {
                    //查找类型和替换类型不同的，不用做处理
                    if (!regxDataType.equals(dataType)) {
                        return;
                    }
                    //替换仅针对基础数据
                    switch (regxDataType) {
                        case TEXT_TYPE -> {
                            ReplaceProp<String> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, String.class);
                            msg.put(propName, replaceProp.getSource().replaceAll(replaceProp.getRegx(), replaceProp.getReplace()));
                        }
                        case BOOL_TYPE -> {
                            ReplaceProp<Boolean> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, Boolean.class);
                            msg.put(propName, replaceProp.regxCalc());
                        }
                        case INTEGER_TYPE -> {
                            ReplaceProp<Integer> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, Integer.class);
                            msg.put(propName, replaceProp.regxCalc());
                        }
                        case LONG_TYPE -> {
                            ReplaceProp<Long> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, Long.class);
                            msg.put(propName, replaceProp.regxCalc());
                        }
                        case  FLOAT_TYPE -> {
                            ReplaceProp<Float> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, Float.class);
                            msg.put(propName, replaceProp.regxCalc());
                        }
                        case DOUBLE_TYPE -> {
                            ReplaceProp<Double> replaceProp = ReplaceProp.create(msg.get(propName), regxValue, value, Double.class);
                            msg.put(propName, replaceProp.regxCalc());
                        }
                    }
                }
                case delete -> msg.remove(propName);
            }
        }

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

        public ChangeCommandEnum getCommand() {
            return command;
        }

        public void setCommand(ChangeCommandEnum command) {
            this.command = command;
        }

        public Object getRegxValue() {
            return regxValue;
        }

        public void setRegxValue(Object regxValue) {
            this.regxValue = regxValue;
        }

        public DataTypeEnum getRegxDataType() {
            return regxDataType;
        }

        public void setRegxDataType(DataTypeEnum regxDataType) {
            this.regxDataType = regxDataType;
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

    public enum ChangeCommandEnum {
        set,
        replace,
        delete;
    }
}
