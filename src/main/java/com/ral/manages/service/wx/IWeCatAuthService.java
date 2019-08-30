package com.ral.manages.service.wx;

import net.sf.json.JSONObject;
import java.util.Map;

public interface IWeCatAuthService {

    //获取code
    String getCode(Map<String,Object> map);
    //获取OpenId
    JSONObject getOpenid(Map<String,Object> map);

}
