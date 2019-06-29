package com.ral.manages.exception;


import com.ral.manages.emun.CloudResponseCode;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 5744893024411154342L;

    /** 请求流水号，有就传，没得就传空 */
    private String reqid = "";
    /** 返回代码，（0-正常，1-网络错误，……） */
    private CloudResponseCode code = CloudResponseCode.BIZ_EXCEPTION;

    public BizException() {
        super();
    }

    public BizException(CloudResponseCode code) {
        super();
        this.code = code;
    }

    public BizException(String msg) {
        super(msg);
    }

    public BizException(CloudResponseCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(String reqid, String msg) {
        super(msg);
        this.reqid = reqid;
    }

    public BizException(String reqid, CloudResponseCode code, String msg) {
        super(msg);
        this.code = code;
        this.reqid = reqid;
    }

    public BizException(String reqid, CloudResponseCode code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.reqid = reqid;
    }

    public BizException(Throwable e) {
        super(e);
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
    }

    public BizException(CloudResponseCode code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public CloudResponseCode getCode() {
        return code;
    }

    public String getReqid() {
        return reqid;
    }

    @Override
    public String toString() {
        String s = "[reqid:" + this.reqid + "]-[code:" + this.code.getCode() + "]";
        return super.toString() + s;
    }
}

