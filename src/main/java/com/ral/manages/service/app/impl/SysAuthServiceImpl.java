package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.service.app.ISysAuthService;
import com.ral.manages.util.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SysAuthServiceImpl implements ISysAuthService , ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<String,Object> sysAuth(Map<String,Object> map) {
        String manages = MapUtil.getString(map,"manages");
        if(StringUtil.isNull(manages)){
            throw new BizException("传入manages参数为空");
        }
        String method = MapUtil.getString(map,"method");
        if(StringUtil.isNull(method)){
            throw new BizException("传入method参数为空");
        }
        String params = Base64Util.Base64Decode(MapUtil.getString(map,"params"));
        Map<String,Object> dataMap = JsonUtil.formatJSON(params);
        String limit = MapUtil.getString(map,"limit");
        String page = MapUtil.getString(map,"page");
        if(!StringUtil.isNull(limit) && !StringUtil.isNull(page)){
            dataMap.put("limit",limit);
            dataMap.put("page",page);
        }
        UnifiedCall service = (UnifiedCall) applicationContext.getBean(manages);
        return service.uCall(method, SetUtil.removeNull(dataMap));
    }
}
