package com.ral.manages.comms.emun;

/**
 *   返回状态类
 *
 *   @author Double
 *   @since  2019-07-18
 */
public enum ResultCode {

    SUCCESS("0", "操作成功"),
    FAIL("1","ERROR"),
    ERROR("999", "ERROR");

    private String code;
    private String result;
    private ResultCode(String code, String result) {
        this.result = result;
        this.code = code;
    }

    public String getCode(){
        return code;
    }
    public String getResult(){
        return result;
    }

}