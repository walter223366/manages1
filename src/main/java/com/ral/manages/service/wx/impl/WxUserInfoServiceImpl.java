package com.ral.manages.service.wx.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.SysConfigurers;
import com.ral.manages.service.wx.WeChatBasis;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.WeChatUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Service("userInfo")
public class WxUserInfoServiceImpl implements WeChatBasis {

    private static final Logger logger = Logger.getLogger(WxUserInfoServiceImpl.class);

    @Autowired
    private SysConfigurers sys;

    @Override
    public Object inWeChat(Map<String,Object> map) {
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
            logger.error("重定向URL编码失败："+e.getMessage());
        }
        logger.info("重定向URL:"+url);
        return url;
    }

    @Override
    public Object outWeChat(Map<String,Object> map) {
        String code = MapUtil.getString(map,"code");
        if(StringUtil.isNull(code)){
            throw new BizException("传入code为空");
        }
        JSONObject result = WeChatUtil.getOpenIdUrl(code);
        String openId = result.optString("openid");
        if(StringUtil.isNull(openId)){
            throw new BizException("获取OpenId为空");
        }
        return result;
    }
}
