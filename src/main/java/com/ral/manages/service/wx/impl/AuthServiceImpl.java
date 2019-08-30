package com.ral.manages.service.wx.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.service.wx.IAuthService;
import com.ral.manages.service.wx.WeChatBasis;
import com.ral.manages.util.MapUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object permissionIn(Map<String,Object> map) {
        String invoke = MapUtil.getString(map,"invoke");
        WeChatBasis authServer = (WeChatBasis) applicationContext.getBean(invoke);
        if (null == authServer) {
            throw new BizException("调用参数非法");
        }
        return authServer.inWeChat(map);
    }

    @Override
    public Object permissionOut(Map<String,Object> map) {
        String invoke = MapUtil.getString(map,"invoke");
        WeChatBasis authServer = (WeChatBasis) applicationContext.getBean(invoke);
        if (null == authServer) {
            throw new BizException("调用参数非法");
        }
        return authServer.outWeChat(map);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
