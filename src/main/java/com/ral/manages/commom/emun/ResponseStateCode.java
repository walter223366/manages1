package com.ral.manages.commom.emun;

/**
 *   <p>功能描述: 结果集状态表</p>
 *   <p>创建时间: 2019-07-18 </p>
 *
 *   @author Double
 */
public enum ResponseStateCode {

    SUCCESS("0", "操作成功"),
    FAIL("1","操作失败"),
    ERROR("999", "系统错误");

    private String code;
    private String msg;
    private ResponseStateCode(String code, String msg) {
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