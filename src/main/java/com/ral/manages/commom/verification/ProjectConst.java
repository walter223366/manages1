package com.ral.manages.commom.verification;

public class ProjectConst {

    public final static String APP_ID = "wx87489829ba0180a1";

    public final static String APP_SECRET = "";


    //获取OpenID接口URL
    public final static String GET_WEBAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


    //获取access_token
    public final static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


}
