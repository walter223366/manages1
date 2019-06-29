package com.ral.manages.exception;

import com.ral.manages.emun.CloudResponseCode;
import net.sf.json.JSONObject;

public class CloudResponse implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** 返回结果说明 */
    private String msg = CloudResponseCode.SUCCESS.getMsg();
    /** 返回的数据 */
    private String rows;
    /** 请求流水号，有就传，没得就传空 */
    private String reqid = "";
    /** 返回代码，（0-正常，1-网络错误，……） */
    private CloudResponseCode code = CloudResponseCode.SUCCESS;

    public CloudResponse() {
        super();
    }

    public CloudResponse(BizException e) {
        super();
        this.code = e.getCode();
        this.reqid = e.getReqid();
        this.msg = e.getMessage();
    }

    public CloudResponse(BizException e, String msg) {
        super();
        this.code = e.getCode();
        this.msg = msg;
    }

    public CloudResponse(BizException e, String reqid, String msg) {
        super();
        this.code = e.getCode();
        this.msg = msg;
        this.reqid = e.getReqid();
    }

    public CloudResponse(String data) {
        super();
        this.rows = data;
    }

    public CloudResponse(String reqid, String data) {
        super();
        this.rows = data;
        this.reqid = reqid;
    }

    public CloudResponse(String msg, CloudResponseCode code) {
        super();
        this.msg = msg;
        this.code = code;
    }

    public CloudResponse(String msg, CloudResponseCode code, String data) {
        super();
        this.msg = msg;
        this.rows = data;
        this.code = code;
    }

    public CloudResponse(String reqid, String msg, CloudResponseCode code, String data) {
        super();
        this.msg = msg;
        this.rows = data;
        this.reqid = reqid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(String data) {
        this.rows = data;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public int getCode() {
        return this.code.getCode();
    }

    public CloudResponseCode getCodeDes() {
        return code;
    }

    public void setCode(CloudResponseCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("reqid", this.reqid);
        json.put("code", this.code.getCode());
        json.put("msg", this.msg);
        json.put("rows", this.rows);
        return json.toString();
    }
}

