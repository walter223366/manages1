package com.ral.manages.service.app;

import java.util.Map;

public interface UnifiedCall {

    Map<String,Object> uCall(String method,Map<String,Object> map);
}
