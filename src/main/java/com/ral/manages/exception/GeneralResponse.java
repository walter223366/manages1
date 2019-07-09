package com.ral.manages.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GeneralResponse<T> {

    /*0:正常 1:异常*/
    private String code;

    /*success:成功 error:失败*/
    private String result;

    /*消息描述*/
    private String message;

    /*数据*/
    private T datas;

    public GeneralResponse(){}

    public GeneralResponse(String code,String result,String message,T datas) {
        this.code = code;
        this.result = result;
        this.message = message;
        this.datas = datas;
    }

    public GeneralResponse success(String message, T datas) {
        return new GeneralResponse("0","SECCESS", message, datas);
    }

    public GeneralResponse fail(String message) {
        return new GeneralResponse("1", "ERROR",message,"");
    }

    public GeneralResponse error(String message) {
        return new GeneralResponse("999", "ERROR",message,"");
    }
}
