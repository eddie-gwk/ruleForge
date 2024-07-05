package com.yunext.common.utils;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/2 17:18
 */
public sealed interface Returned<T> {

    Returned.Undefined UNDEFINED = new Undefined<>();

    record ReturnValue<T>(T returnValue) implements Returned<T> {}

    record Undefined<T>() implements Returned<T> {}
}
