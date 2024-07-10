package com.yunext.common.utils;

import org.modelmapper.ModelMapper;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 10:53
 */
public class ModelMapperUtil {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public static <T> T map(Object obj, Class<T> clazz) {
        return MODEL_MAPPER.map(obj, clazz);
    }

}
