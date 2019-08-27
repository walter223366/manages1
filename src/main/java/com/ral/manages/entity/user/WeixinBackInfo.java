package com.ral.manages.entity.user;

import lombok.Data;
import java.io.Serializable;

@Data
public class WeixinBackInfo implements Serializable {

    private static final long serialVersionUID = -5460446574705536950L;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String errcode;
    private String errmsg;

}
