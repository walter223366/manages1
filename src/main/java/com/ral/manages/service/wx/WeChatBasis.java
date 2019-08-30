package com.ral.manages.service.wx;

import java.util.Map;

public interface WeChatBasis {

     Object inWeChat(Map<String,Object> map);

     Object outWeChat(Map<String,Object> map);
}
