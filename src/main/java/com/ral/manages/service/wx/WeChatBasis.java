package com.ral.manages.service.wx;

import java.util.Map;

public interface WeChatBasis {

     String inWeChat(Map<String,Object> map);

     String outWeChat(Map<String,Object> map);
}
