package com.yunext.common.enums;


import com.yunext.common.utils.ModelMapperUtil;
import com.yunext.common.utils.StringUtil;

import java.util.Date;
import java.util.Map;

/**
 * @Author: panhui
 * @Description: 数据类型
 * @Date: 2021/2/4 17:27
 */
public enum DataTypeEnum {
    MSG_TYPE("msg", "msg"),
    /**
     * 整数32
     */
    INTEGER_TYPE("int", "int32 (整数型)"),

    /**
     * 整数64
     */
    LONG_TYPE("long", "long64 (整数型)"),
    /**
     * 单精度浮点
     */
    FLOAT_TYPE("float", "float (单精度浮点型)"),
    /**
     * 双精度浮点
     */
    DOUBLE_TYPE("double", "float (双精度浮点型)"),
    /**
     * 文本字符串
     */
    TEXT_TYPE("text", "text (字符串)"),
    /**
     * String类型UTC毫秒
     */
    DATE_TYPE("date", "date (时间型)"),
    /**
     * 0或1的int类型
     */
    BOOL_TYPE("bool", "bool (布尔型)"),
    /**
     * int类型，枚举项定义方法与bool类型定义0和1的值方法相同
     */
    ENUM_TYPE("enum", "enum (枚举型)"),
    /**
     * 结构体类型，可包含前面7种类型，下面使用"specs":[{}]描述包含的对象
     */
    STRUCT_TYPE("struct", "struct (结构体)"),
    /**
     * 数组类型，支持int、double、float、text、struct
     */
    ARRAY_TYPE("array", "array（数组型）"),
    UNKNOWN_TYPE("unknown", "unknown (未知类型)");
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    DataTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void conversion(Object prop, Object value, Map<Object, Object> msg) {
        switch (this) {
            case MSG_TYPE -> msg.put(prop, msg.get(value));
            case INTEGER_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Integer.class));
            case LONG_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Long.class));
            case FLOAT_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Float.class));
            case DOUBLE_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Double.class));
            case TEXT_TYPE -> msg.put(prop, ModelMapperUtil.map(value, String.class));
            case DATE_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Date.class));
            case BOOL_TYPE -> msg.put(prop, ModelMapperUtil.map(value, Boolean.class));
            case UNKNOWN_TYPE, ENUM_TYPE, ARRAY_TYPE, STRUCT_TYPE -> msg.put(prop, value);
        }
    }

    public static String getNameByCode(String code) {
        for (DataTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return "";
    }

    public static String valueCast(String code, String defaultValue) {
        String value = defaultValue;
        if (StringUtil.isAnyEmpty(code, defaultValue)) return value;
        if (code.equals(DataTypeEnum.INTEGER_TYPE.getCode())) {
            value = String.valueOf(Double.valueOf(defaultValue).intValue());
        }
        if (code.equals(DataTypeEnum.DOUBLE_TYPE.getCode())) {
            value = String.valueOf(Double.valueOf(defaultValue));
        }
        return value;
    }

    public static boolean isDoubleValue(String code) {
        return DataTypeEnum.DOUBLE_TYPE.getCode().equals(code);
    }

    public static DataTypeEnum getByCode(String code) {
        for (DataTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN_TYPE;
    }

    public static boolean isEnum(String code) {
        return ENUM_TYPE.code.equals(code);
    }

    public static boolean isMsg(String code) {
        return MSG_TYPE.code.equals(code);
    }

    public static boolean isText(String code) {
        return TEXT_TYPE.code.equals(code);
    }

    public boolean valueTypeof(Object msgValue) {
        return switch (this) {
            case BOOL_TYPE -> msgValue instanceof Boolean;
            case TEXT_TYPE -> msgValue instanceof String;
            case INTEGER_TYPE -> msgValue instanceof Integer;
            case LONG_TYPE -> msgValue instanceof Long;
            case FLOAT_TYPE -> msgValue instanceof Float;
            case DOUBLE_TYPE -> msgValue instanceof Double;
            case DATE_TYPE -> msgValue instanceof Date;
            case STRUCT_TYPE -> true;
            case MSG_TYPE, ENUM_TYPE, ARRAY_TYPE, UNKNOWN_TYPE -> false;
        };
    }
}
