package com.ral.manages.util;

import com.ral.manages.mapper.sys.SysConfigurers;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeChatUtil.class);
    @Autowired
    private static SysConfigurers sys;

    //获取code地址
    public static String getAuthUrl(String redirectUrl,String scope){
        String url = sys.getWeChat_authorize();
        url = url.concat("?appid="+sys.getWeChat_appId())
                .concat("&redirect_uri="+redirectUrl)
                .concat("&response_type=code&scope="+scope)
                .concat("&state="+sys.getWeChat_state())
                .concat("&connect_redirect=1#wechat_redirect");
        return url;
    }

    public static JSONObject getOpenIdUrl(String code){
        String url = sys.getWeChat_accessToken();
        Map<String,String> map = new HashMap<String,String>();
        map.put("appid",sys.getWeChat_appId());
        map.put("secret",sys.getWeChat_appSecret());
        map.put("code",code);
        map.put("grant_type",sys.getWeChat_grantType());
        String str = HttpsClientUtils.doPostMap(url,map,sys.getCharsetName());
        JSONObject result = JSONObject.fromObject(str);
        return result;
    }
}
