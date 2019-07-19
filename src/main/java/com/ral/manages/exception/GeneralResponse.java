package com.ral.manages.exception;

import com.ral.manages.util.Base64Util;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class GeneralResponse<T> {

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
        return new GeneralResponse("0","SECCESS", msg, base64);
    }

    /*操作成功，无数据*/
    public static GeneralResponse successNotdatas(String msg) {
        return new GeneralResponse("0","SECCESS", msg, "");
    }

    /*操作失败*/
    public static GeneralResponse fail(String msg) {
        return new GeneralResponse("1", "ERROR",msg,"");
    }

    /*系统异常*/
    public static GeneralResponse error(String msg) {
        return new GeneralResponse("999", "ERROR",msg,"");
    }
}
