package com.ral.manages.commom.verification;

public class ProjectConst {

    public final static String MANAGESURL = "http://www.aiurorigin.com/manages/";

    //TOKEN自定义令牌
    public final static String TOKEN = "aiurorigin";
    //APP_ID
    public final static String APP_ID = "wx87489829ba0180a1";
    //APP_SECRET
    public final static String APP_SECRET = "ea195645c7814eb60923dbc960e9f482";

    public final static String SNSAPI_BASE = "snsapi_base";
    public final static String SNSAPI_USERINFO = "snsapi_userinfo";

    //获取code地址
    public final static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //获取access_token地址 获取OpenId
    public final static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //检验token是否有效地址
    public final static String GET_CHECKTOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
    //刷新token地址
    public final static String GET_REFRESHTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    //获取微信用户信息地址(用户授权)
    public final static String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
}
