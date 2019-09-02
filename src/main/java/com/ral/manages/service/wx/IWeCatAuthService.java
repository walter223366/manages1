package com.ral.manages.service.wx;

import net.sf.json.JSONObject;
import java.util.Map;

public interface IWeCatAuthService {

    //获取code
    String getCode(Map<String,Object> map);
    //获取OpenId
    JSONObject getOpenId(Map<String,Object> map);
    //获取微信信息
    JSONObject getUserInfo(Map<String,Object> map,JSONObject json);
}
