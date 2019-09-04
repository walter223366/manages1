package com.ral.manages.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource(value = "classpath:/config/sys.properties")
@ConfigurationProperties(prefix="sys")
@Component
public class SysConfigurers{

    private String charsetName;//字符编码集
    private String manages_url;//项目路径
    private String web_url;//web项目地址
    private String weChat_callBack;
    private String error_url;//错误页面
    private String weChat_token;//微信token
    private String weChat_appId;//微信app_Id
    private String weChat_appSecret;//微信app_Secret
    private String weChat_scopeBase;//静默模式
    private String weChat_scopeUserInfo;//授权模式
    private String weChat_state;//微信state
    private String weChat_grantCode;//微信grant_type
    private String weChat_grantClient;
    private String weChat_authorize;//获取code路径
    private String weChat_accessToken;//获取access_token、OpenId
    private String weChat_auth;//检验access_token是否有效地址
    private String weChat_refreshToken;//刷新access_token地址
    private String wehChat_userInfo;//获取微信用户信息地址
    private String wehChat_getAccessToken;//获取access_token地址

}
