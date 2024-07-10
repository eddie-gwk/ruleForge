package com.yunext.common.base;

import com.yunext.common.node.common.ChangeNode;
import com.yunext.common.utils.ModelMapperUtil;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 14:51
 */
public class ReplaceProp<T> {

    private T source;
    private T regx;
    private T replace;

    /**
     * 计算是否可替换
     * @return T
     */
    public T regxCalc() {
        return source.equals(regx) ? replace : source;
    }

    public ReplaceProp(T source, T reg, T replace) {
        this.source = source;
        this.regx = reg;
        this.replace = replace;
    }

    public static <T> ReplaceProp<T> create(Object source, Object regx, Object replace, Class<T> clazz) {
        T s = ModelMapperUtil.map(source, clazz);
        T r = ModelMapperUtil.map(regx, clazz);
        T rp = ModelMapperUtil.map(replace, clazz);
        return new ReplaceProp<>(s, r, rp);
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public T getRegx() {
        return regx;
    }

    public void setRegx(T regx) {
        this.regx = regx;
    }

    public T getReplace() {
        return replace;
    }

    public void setReplace(T replace) {
        this.replace = replace;
    }
}
