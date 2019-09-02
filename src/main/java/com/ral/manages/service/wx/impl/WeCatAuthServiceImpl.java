package com.ral.manages.service.wx.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.SysConfigurers;
import com.ral.manages.service.wx.IWeCatAuthService;
import com.ral.manages.util.HttpsClientUtils;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeCatAuthServiceImpl implements IWeCatAuthService {

    private static final Logger log = Logger.getLogger(WeCatAuthServiceImpl.class);
    @Autowired
    private SysConfigurers sys;

    /**
     * 获取code地址
     * @param map map
     * @return String
     */
    @Override
    public String getCode(Map<String,Object> map) {
        String type = MapUtil.getString(map,"type");
        String redirectUrl;
        String scope;
        if(type.equals("userInfo")){
            redirectUrl = sys.getWeChat_callBackUser();
            scope = sys.getWeChat_scopeUserInfo();
        }else{
            redirectUrl = sys.getWeChat_callBackId();
            scope = sys.getWeChat_scopeBase();
        }
        String url = "";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,sys.getCharsetName());
            url = sys.getWeChat_authorize();
            url = url.concat("?appid="+sys.getWeChat_appId())
                    .concat("&redirect_uri="+redirectUrl)
                    .concat("&response_type=code&scope="+scope)
                    .concat("&state="+sys.getWeChat_state())
                    .concat("#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            log.error("重定向URL编码失败："+e.getMessage());
        }
        log.info("微信重定向URL:"+url);
        return url;
    }

    /**
     * 获取OpenId
     * @param map map
     * @return JSONObject
     */
    @Override
    public JSONObject getOpenId(Map<String,Object> map){
        String code = MapUtil.getString(map,"code");
        if(StringUtil.isNull(code)){
            throw new BizException("传入code为空");
        }
        String url = sys.getWeChat_accessToken();
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("appid",sys.getWeChat_appId());
        paramsMap.put("secret",sys.getWeChat_appSecret());
        paramsMap.put("code",code);
        paramsMap.put("grant_type",sys.getWeChat_grantCode());
        JSONObject result = new JSONObject();
        try{
            String str = HttpsClientUtils.doPostMap(url,paramsMap,sys.getCharsetName());
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            throw new BizException("获取微信OpenId失败："+e.getMessage());
        }
        String openId = result.optString("openid");
        if(StringUtil.isNull(openId)){
            throw new BizException("获取OpenId失败");
        }
        log.info("获取微信OpenId："+result);
        return result;
    }

    /**
     * 获取微信信息
     * @param map map
     * @return JSONObject
     */
    @Override
    public JSONObject getUserInfo(Map<String,Object> map,JSONObject json) {
        String code = MapUtil.getString(map,"code");
        if(StringUtil.isNull(code)){
            throw new BizException("传入code为空");
        }
        String url = sys.getWehChat_userInfo();
        url = url.concat("?access_token="+json.optString("access_token"))
                .concat("&openid="+json.optString("openid"))
                .concat("&lang=zh_CN");
        JSONObject result = new JSONObject();
        try{
            String str = HttpsClientUtils.doGet(url,sys.getCharsetName());
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            throw new BizException("获取微信OpenId失败："+e.getMessage());
        }
        return result;
    }

}
