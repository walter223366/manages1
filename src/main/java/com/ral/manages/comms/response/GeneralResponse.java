package com.ral.manages.comms.response;

import com.ral.manages.comms.emun.ResponseStateCode;
import com.ral.manages.util.Base64Util;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Data
@Component
public class GeneralResponse<T> implements Serializable {

    private static final long serialVersionUID = -8959290016963913460L;
    private final static String SUCCESS = "SUCCESS";
    private final static String ERROR = "ERROR";
    private final static String ROWS = "";


    /*0:正常 1:异常*/
    private String code;

    /*success:成功 error:失败*/
    private String result;

    /*消息描述*/
    private String msg;

    /*数据*/
    private T rows;

    public GeneralResponse(){}

    public GeneralResponse(String code,String result,String msg,T rows) {
        this.code = code;
        this.result = result;
        this.msg = msg;
        this.rows = rows;
    }

    /*操作成功，有数据*/
    public static GeneralResponse success(String msg, Map<String,Object> rows) {
        JSONObject json = JSONObject.fromObject(rows);
        String base64 = Base64Util.Base64Encode(json.toString());
        return new GeneralResponse(ResponseStateCode.SUCCESS.getCode(),SUCCESS,msg,base64);
    }

    /*操作成功，无数据*/
    public static GeneralResponse successNotdatas(String msg) {
        return new GeneralResponse(ResponseStateCode.SUCCESS.getCode(),SUCCESS, msg,ROWS);
    }

    /*操作失败*/
    public static GeneralResponse fail(String msg) {
        return new GeneralResponse(ResponseStateCode.FAIL.getCode(),ERROR,msg,ROWS);
    }

    /*系统异常*/
    public static GeneralResponse error(String msg) {
        return new GeneralResponse(ResponseStateCode.ERROR.getCode(),ERROR,msg,ROWS);
    }
}
