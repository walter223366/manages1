package com.ral.manages.entity;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.util.Base64Util;
import lombok.Data;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class Result<T> extends HashMap<String,Object> {

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
    private Object rows;

    public Result(){
        super();
        this.put("code", ResultCode.SUCCESS.getCode());
        this.put("result", SUCCESS);
        this.put("msg", ResultCode.SUCCESS.getMsg());
        this.put("rows",ROWS);
    }

    public void setCode(String code) {
        this.put("code",code);
    }

    public void setResult(String result) {
        this.put("result",result);
    }

    public void setMsg(String msg) {
        this.put("msg",msg);
    }

    public void setRows(Object rows) {
        if(rows != null){
            this.put("rows",rows);
        }
    }



    public Result(String code, String result, String msg, T rows) {
        this.code = code;
        this.result = result;
        this.msg = msg;
        this.rows = rows;
    }

    /*操作成功，有数据*/
    public static Result success(String msg, Map<String,Object> rows) {
        JSONObject json = JSONObject.fromObject(rows);
        String base64 = Base64Util.Base64Encode(json.toString());
        return new Result(ResultCode.SUCCESS.getCode(),SUCCESS,msg,base64);
    }

    /*操作成功，无数据*/
    public static Result successNotdatas(String msg) {
        return new Result(ResultCode.SUCCESS.getCode(),SUCCESS, msg,ROWS);
    }

    /*操作失败*/
    public static Result fail(String msg) {
        return new Result(ResultCode.FAIL.getCode(),ERROR,msg,ROWS);
    }

    /*系统异常*/
    public static Result error(String msg) {
        return new Result(ResultCode.ERROR.getCode(),ERROR,msg,ROWS);
    }
}
