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

    @Override
    public String getCode(Map<String,Object> map) {
        String redirectUrl = sys.getWeb_url();
        String url = "";
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,sys.getCharsetName());
            String scope = sys.getWeChat_scopeBase();
            url = sys.getWeChat_authorize();
            url = url.concat("?appid="+sys.getWeChat_appId())
                    .concat("&redirect_uri="+redirectUrl)
                    .concat("&response_type=code&scope="+scope)
                    .concat("&state="+sys.getWeChat_state())
                    .concat("&connect_redirect=1#wechat_redirect");
        } catch (UnsupportedEncodingException e) {
            log.error("重定向URL编码失败："+e.getMessage());
        }
        log.info("重定向URL:"+url);
        return url;
    }

    @Override
    public JSONObject getOpenid(Map<String,Object> map){
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
        String str = HttpsClientUtils.doPostMap(url,paramsMap,sys.getCharsetName());
        JSONObject result = JSONObject.fromObject(str);
        log.info("获取WeCatInfo："+result);
        return result;
    }
}
