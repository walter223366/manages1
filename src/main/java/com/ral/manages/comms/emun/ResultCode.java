package com.ral.manages.comms.emun;

/**
 *   <p>功能描述: 结果集状态表</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
public enum ResultCode {

    SUCCESS("0", "操作成功"),
    FAIL("1","ERROR"),
    ERROR("999", "ERROR");

    private String code;
    private String msg;
    private ResultCode(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

}