package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.IBaseNPCMapper;
import com.ral.manages.service.app.UnifiedCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("baseNpc")
public class BaseNPCServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(BaseNPCServiceImpl.class);
    @Autowired
    private IBaseNPCMapper baseNPCMapper;

    /**
     * 处理人物管理(NPC)
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY:
                break;
            default:
                throw new BizException("传入该方法不存在");
        }
        return result;
    }

    private Map<String,Object> baseNPCPagingQuery(Map<String,Object> map){
        return null;
    }
}
