package com.ral.manages.emun;

public enum CloudResponseCode {
    SUCCESS(0, "成功"),
    BIZ_EXCEPTION(1, "业务异常"),
    DB_EXCEPTION(2, "系统DB异常"),
    HTTPTYPE_EXCEPTION(3,"请求类型不正确"),
    NOTREADABLE_EXCEPTION(4, "请求参数无法解析"),
    NETWORK_EXCEPTION(5, "子系统网络连接错误"),
    UNKNOW_EXCEPTION(999, "未知的错误")
    ;
    private int code;
    private String msg;
    private CloudResponseCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}