package com.yunext.api.constant;



/**
 * @Author: 潘辉
 * @Description:
 * @Date 2019/5/8
 */
public enum ResultApiCode {
    /**
     * 请求成功
     */
    SUCCESS(100, "api.success", true),

    /**
     * 001 - 029 权限类错误
     */
    AUTH_FORBIDDEN(101, "api.auth_forbidden", false),
    AUTH_NOT_RIGHT(102, "api.auth_not_right", false),
    AUTH_NOT_LOGIN(103, "api.auth_not_login", false),
    AUTH_TOKEN_EXPIRE(104, "api.auth_token_expire", false),
    AUTH_SIGN_ERROR(105, "api.auth_sign_error", false),
    AUTH_IP_FORBIDDEN(106, "api.auth_ip_forbidden", false),
    SAVE_FAIL(107, "api.save_fail", false),


    NOT_NULL(201, "api.not_null", false),

    WX_OPENID_FAIL(301,"api.wx_openid_fail",false),
    GET_JSAPISIGNATURE_FAIL(302,"api.get_jsapisignature_fail",false),
    WX_MA_RAW_DATA_ERROR(303,"api.wx_ma_raw_data_error",false),

    REQUEST_EXPIRE(304,"api.request_expire",false),
    REQUEST_PARAM_ERR(305,"api.request_param_err",false),
    REQUEST_PARAM_LOST(306,"api.request_param_lost",false),

    OTA_FIRWARE_NO_EXISTS(400, "api.ota_firmware_no_exists", false),
    OTA_FIRWARE_HAD_EXISTS(401, "api.ota_firmware_had_exists", false),
    OTA_FIRWARE_HAD_TASK(402, "api.ota_firmware_had_task", false),
    OTA_FIRWARE_DELETE_FAIL(403, "api.ota_firmware_delete_fail", false),

    OTA_URGRADE_TASK_DELETE_FAIL(404, "api.ota_upgrade_task_delete_fail", false),
    OTA_URGRADE_TASK_SAVE_FAIL(405, "api.ota_upgrade_task_save_fail", false),
    REQUEST_REPEAT(406, "api.request_repeat", false),
    ACCOUNT_PHONE_REPEAT(407, "api.account_phone_repeat", false),
    ERROR(999, "api.error", false);


    private int code;
    private String msg;
    private boolean success;

    ResultApiCode(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
