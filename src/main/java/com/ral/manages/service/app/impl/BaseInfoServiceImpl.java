package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.mapper.app.IBaseInfoMapper;
import com.ral.manages.service.app.IBaseInfoService;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("baseInfo")
public class BaseInfoServiceImpl implements UnifiedCall,IBaseInfoService {

    private static final Logger log = LoggerFactory.getLogger(BaseInfoServiceImpl.class);
    @Autowired
    private IBaseInfoMapper baseInfoMapper;

    /**
     * 处理人物管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        return null;
    }

    /**
     * 查询人物信息
     *
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> baseInfo(Map<String,Object> map) {
        String userId = MapUtil.getString(map,"id");
        List<Map<String,Object>> infoList = baseInfoMapper.getBaseInfo(userId);

        return null;
    }
}
