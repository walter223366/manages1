package com.ral.manages.entity;

import com.ral.manages.comms.emun.ResultCode;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.HashMap;

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
        this.put("msg", ResultCode.SUCCESS.getResult());
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
}
