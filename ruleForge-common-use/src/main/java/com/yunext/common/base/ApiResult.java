package com.yunext.common.base;


import com.yunext.common.utils.StringUtil;

public class ApiResult<T> {
    private int code;
    //使用国际化时开启
    private String msg;
    private T data;
    private boolean success;

    public ApiResult() {
    }

    public ApiResult(ResultApiCode resultApiCode) {
        this.code = resultApiCode.getCode();
        this.msg = resultApiCode.getMsg();
        this.success = resultApiCode.isSuccess();
    }

    public static ApiResult result(ResultDto resultDto) {
        if (resultDto.isSuccess()) {
            ApiResult apiResult = new ApiResult(ResultApiCode.SUCCESS);
            if (resultDto.getData() != null) {
                apiResult.setData(resultDto.getData());
            }
            return apiResult;
        } else {
            ApiResult apiResult = new ApiResult(ResultApiCode.ERROR);
            if (StringUtil.isNotEmpty(resultDto.getErrMsg())) {
                apiResult.setMsg(resultDto.getErrMsg());
            }
            return apiResult;
        }
    }

    public static ApiResult success(Object data) {
        ApiResult apiResult = new ApiResult(ResultApiCode.SUCCESS);
        apiResult.setData(data);
        return apiResult;
    }

    public static ApiResult success(String message, Object data) {
        ApiResult apiResult = new ApiResult(ResultApiCode.SUCCESS);
        apiResult.setData(data);
        apiResult.setMsg(message);
        return apiResult;
    }

    public static ApiResult fail(String message) {
        ApiResult apiResult = new ApiResult(ResultApiCode.ERROR);
        if(message.length()<100){
            apiResult.setMsg(message);
        }
        return apiResult;
    }

    public static ApiResult fail(Object obj) {
        ApiResult apiResult = new ApiResult(ResultApiCode.ERROR);
        apiResult.setData(obj);
        return apiResult;
    }

    public static ApiResult fail() {
        ApiResult apiResult = new ApiResult(ResultApiCode.ERROR);
        return apiResult;
    }

    public static ApiResult fail(ResultApiCode code) {
        return new ApiResult(code);
    }


    public static ApiResult success() {
        ApiResult apiResult = new ApiResult(ResultApiCode.SUCCESS);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
