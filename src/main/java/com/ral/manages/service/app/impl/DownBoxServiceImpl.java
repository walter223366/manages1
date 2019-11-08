package com.ral.manages.service.app.impl;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.*;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("downBox")
public class DownBoxServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(DownBoxServiceImpl.class);
    @Autowired
    private IEffectMapper effectMapper;
    @Autowired
    private IKongFuMapper kongFuMapper;
    @Autowired
    private ISchoolMapper schoolMapper;
    @Autowired
    private IMoveMapper moveMapper;
    @Autowired
    private IAccountMapper accountMapper;


    /**
     * 下拉框处理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.MOVEDOWNBOX: result = moveDownBox(map);
                break;
            case ProjectConst.EFFECTDOWNBOX: result = effectDownBox(map);
                break;
            case ProjectConst.KONGFUDOWNBOX: result = kongFuDownBox(map);
                break;
            case ProjectConst.SCHOOLDOWNBOX: result = schoolDownBox(map);
                break;
            case ProjectConst.ACCOUNTDOWNBOX: result = accountDownBox(map);
                break;
            case ProjectConst.MOVENULDOWNBOX: result = moveNulDownBox(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*招式下拉框*/
    private Map<String,Object> moveDownBox(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        resultList = moveMapper.moveQueryMarquee(map);
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*效果下拉框*/
    private Map<String,Object> effectDownBox(Map<String,Object> map) {
        List<Map<String,Object>> resultList = effectMapper.effectQueryMarquee(map);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*功夫下拉框*/
    private Map<String,Object> kongFuDownBox(Map<String,Object> map) {
        List<Map<String,Object>> resultList = kongFuMapper.kongFuQueryMarquee(map);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*门派下拉框*/
    private Map<String,Object> schoolDownBox(Map<String,Object> map){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = schoolMapper.schoolQueryMarquee();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*账号下拉框*/
    private Map<String,Object> accountDownBox(Map<String,Object> map){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList =resultList = accountMapper.accountQueryMarquee(map);
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*招式下拉框-单*/
    private Map<String,Object> moveNulDownBox(Map<String,Object> map){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        resultList = moveMapper.moveQueryKFIsNull();
        if(SetUtil.isMapNull(map)){
            resultMap.put("data",resultList);
        }else{
            String name = MapUtil.getString(map,"name");
            if(StringUtil.isNull(name)){
                throw new BizException("传入功夫名称为空");
            }
            Map<String,Object> kongMap = kongFuMapper.kongFuEditQuery(map);
            List<String> list = SetUtil.getListSplit(MapUtil.getString(kongMap,"kongfu_zhaoshi"));
            if(list.size() > 0){
                for(int i=0; i<list.size(); i++){
                    Map<String,Object> objMap = moveMapper.moveQueryExistKF(list.get(i));
                    if(resultList.size() > 0){
                        for(int j=0; j<resultList.size(); j++){
                            Map<String,Object> resMap = resultList.get(j);
                            if(!resMap.equals(objMap)){
                                resultList.add(objMap);
                            }
                        }
                    }else {
                        resultList.add(objMap);
                    }
                }
            }
            resultMap.put("data",resultList);
        }
        return resultMap;
    }
}