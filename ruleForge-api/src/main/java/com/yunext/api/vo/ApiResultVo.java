package com.yunext.api.vo;


import com.yunext.api.dto.ResultDto;
import com.yunext.api.constant.ResultApiCode;
import com.yunext.common.utils.StringUtil;

public class ApiResultVo<T> {
    private int code;
    //使用国际化时开启
    private String msg;
    private T data;
    private boolean success;

    public ApiResultVo() {
    }

    public ApiResultVo(ResultApiCode resultApiCode) {
        this.code = resultApiCode.getCode();
        this.msg = resultApiCode.getMsg();
        this.success = resultApiCode.isSuccess();
    }

    public static ApiResultVo result(ResultDto resultDto) {
        if (resultDto.isSuccess()) {
            ApiResultVo apiResult = new ApiResultVo(ResultApiCode.SUCCESS);
            if (resultDto.getData() != null) {
                apiResult.setData(resultDto.getData());
            }
            return apiResult;
        } else {
            ApiResultVo apiResult = new ApiResultVo(ResultApiCode.ERROR);
            if (StringUtil.isNotEmpty(resultDto.getErrMsg())) {
                apiResult.setMsg(resultDto.getErrMsg());
            }
            return apiResult;
        }
    }

    public static <T> ApiResultVo<T> success(T data) {
        ApiResultVo<T> apiResult = new ApiResultVo<>(ResultApiCode.SUCCESS);
        apiResult.setData(data);
        return apiResult;
    }

    public static ApiResultVo success(String message, Object data) {
        ApiResultVo apiResult = new ApiResultVo(ResultApiCode.SUCCESS);
        apiResult.setData(data);
        apiResult.setMsg(message);
        return apiResult;
    }

    public static ApiResultVo fail(String message) {
        ApiResultVo apiResult = new ApiResultVo(ResultApiCode.ERROR);
        if(message.length()<100){
            apiResult.setMsg(message);
        }
        return apiResult;
    }

    public static ApiResultVo fail(Object obj) {
        ApiResultVo apiResult = new ApiResultVo(ResultApiCode.ERROR);
        apiResult.setData(obj);
        return apiResult;
    }

    public static ApiResultVo fail() {
        ApiResultVo apiResult = new ApiResultVo(ResultApiCode.ERROR);
        return apiResult;
    }

    public static ApiResultVo fail(ResultApiCode code) {
        return new ApiResultVo(code);
    }


    public static ApiResultVo success() {
        ApiResultVo apiResult = new ApiResultVo(ResultApiCode.SUCCESS);
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
