package com.yunext.common.base;

import com.yunext.common.utils.Returned;

import java.io.Serializable;

public class ResultDto<T> implements Serializable {

    private static final long serialVersionUID = -1006670461583800363L;

    /**
     * 是否执行成功
     */
    private boolean success;
    /**
     * 执行失败时，返回的错误信息
     */
    private String errMsg;
    /**
     * 如果执行成功有需要返回数据，则通过该字段返回
     */
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Returned<T> getData() {
        if (this.data == null) {
            return Returned.UNDEFINED;
        }
        return new Returned.ReturnValue<>(this.data);
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultDto(boolean success, String errMsg, T data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    public ResultDto() {
    }

    public static <T> ResultDto<T> success() {
        return success(null);
    }

    public static <T> ResultDto<T> success(T data) {
        return new ResultDto<>(true, null, data);
    }

    public static <T> ResultDto<T> fail(String errMsg) {
        return new ResultDto<>(false, errMsg, null);
    }

    public static <T> ResultDto<T> excute(boolean success) {
        return excute(success, "执行失败");
    }

    public static <T> ResultDto<T> excute(boolean success, String errMsg) {
        return success ? success() : fail(errMsg);
    }
}
