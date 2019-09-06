package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.IAccountMapper;
import com.ral.manages.mapper.app.IBaseInfoMapper;
import com.ral.manages.mapper.app.ISchoolMapper;
import com.ral.manages.service.app.IBaseInfoService;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.SetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseInfo")
public class BaseInfoServiceImpl implements UnifiedCall,IBaseInfoService {

    private static final Logger log = LoggerFactory.getLogger(BaseInfoServiceImpl.class);
    @Autowired
    private IBaseInfoMapper baseInfoMapper;
    @Autowired
    private ISchoolMapper schoolMapper;
    @Autowired
    private IAccountMapper accountMapper;

    /**
     * 处理人物管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        //TODO
        return result;
    }



    /**
     * 查询人物信息
     *
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> baseInfo(Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        String userId = MapUtil.getString(map,"id");
        List<Map<String,Object>> infoList = baseInfoMapper.queryBaseInfo(userId);
        if(SetUtil.isListNull(infoList)){
            return result;
        }
        for(Map<String,Object> baseMap : infoList){
            Map<String,Object> infoMap = SetUtil.clearValueNullToMap(baseMap);
            String lastTime = MapUtil.getString(infoMap,"last_time");
            if(lastTime.equals("1")){
                String baseId = MapUtil.getString(map,"id");
                Map<String,Object> schoolMap = new HashMap<String,Object>();
                schoolMap = schoolMapper.schoolIdQuery(infoMap);
                result.put("schoolInfo",SetUtil.clearValueNullToMap(schoolMap));//门派信息
                List<Map<String,Object>> attainMap = baseInfoMapper.queryAttainmentsInfo(baseId);
                result.put("attainInfo",SetUtil.clearValueNullToList(attainMap));//造诣信息
                List<Map<String,Object>> battleMap = baseInfoMapper.queryBattleInfo(baseId);
                result.put("battleInfo",SetUtil.clearValueNullToList(battleMap));//battle信息
                List<Map<String,Object>> potentialMap = baseInfoMapper.queryPotentialInfo(baseId);
                result.put("potentialInfo",SetUtil.clearValueNullToList(potentialMap));//学识表
                List<Map<String,Object>> relationMap = baseInfoMapper.queryRelationInfo(baseId);
                result.put("relationInfo",SetUtil.clearValueNullToList(relationMap));//关系表
                result.putAll(infoMap);
            }
        }
        return result;
    }
}
