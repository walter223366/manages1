package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.entity.SysConfigurers;
import com.ral.manages.mapper.app.*;
import com.ral.manages.service.app.IBaseService;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("base")
public class BaseServiceImpl implements UnifiedCall, IBaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
    @Autowired
    private IBaseMapper baseMapper;
    @Autowired
    private ISchoolMapper schoolMapper;
    @Autowired
    private IAccountMapper accountMapper;
    @Autowired
    private IKongFuMapper kongFuMapper;
    @Autowired
    private SysConfigurers sys;

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
        switch (method){
            case ProjectConst.PAGINGQUERY: result = basePagingQuery(map);
                break;
            case ProjectConst.SEEDETAILS: result = baseSee(map);
                break;
            case ProjectConst.INSERT: result = baseInsert(map);
                break;
            case ProjectConst.UPDATE: result = baseUpdate(map);
                break;
            case ProjectConst.DELETE: result = baseDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> basePagingQuery(Map<String,Object> map){
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> baseList = baseMapper.basePagingQuery(map);
        for(Map<String,Object> baseMap : baseList){
            int sex = MapUtil.getInt(baseMap,"sex");
            String sexValue = (sex== TableCode.SEX_ZERO.getCode()?TableCode.SEX_ZERO.getName():TableCode.SEX_ONE.getName());
            baseMap.put("sex",sexValue);
            String schoolId = MapUtil.getString(baseMap,"school_id");
            if(!StringUtil.isNull(schoolId)){
                Map<String,Object> shcoolMap = schoolMapper.schoolIdQuery(baseMap);
                baseMap.put("schoolName",MapUtil.getString(shcoolMap,"name"));
            }
            String userId = MapUtil.getString(baseMap,"user_id");
            if(!StringUtil.isNull(userId)){
                Map<String,Object> userMap = accountMapper.accountQueryName(userId);
                baseMap.put("userName",MapUtil.getString(userMap,"account"));
            }
        }
        return PageBean.resultPage(page.getTotal(),baseList);
    }

    /*新增*/
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
    public Map<String,Object> baseInsert(Map<String,Object> map) {
        VerificationUtil.verificationBase(map);
        int count = baseMapper.baseIsExist(map);
        if (count > 0) {
            throw new BizException("新增失败，人物名称已存在");
        }
        map.put("user_id",sys.getNpcAccount());//TODO NPC人物固定账号ID
        map.put("cancellation",TableCode.CANCELLATION_ZERO.getCode());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        String id = StringUtil.getUUID();
        map.put("id",id);
        Map<String,Object> vMap = (Map<String,Object>) map.get("virtue");
        Map<String,Object> wMap = (Map<String,Object>) map.get("weapon");
        Map<String,Object> pMap = (Map<String,Object>) map.get("potent");
        map.put("kongfu_have_id",Base64Util.Base64Encode(map.get("kongfu_have_id").toString()));
        try {
            baseMapper.baseInsert(SetUtil.turnNull(map));
            if(!SetUtil.isMapNull(vMap)){
                vMap.put("id",StringUtil.getUUID());
                vMap.put("character_id",id);
                baseMapper.baseExtInsert(SetUtil.turnNull(vMap));
            }
            if(!SetUtil.isMapNull(wMap)){
                wMap.put("id",StringUtil.getUUID());
                wMap.put("character_id",id);
                baseMapper.baseAttInsert(SetUtil.turnNull(wMap));
            }
            if(!SetUtil.isMapNull(pMap)){
                pMap.put("id",StringUtil.getUUID());
                pMap.put("character_id",id);
                baseMapper.basePotInsert(SetUtil.turnNull(pMap));
            }
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败");
        }
    }

    /*修改*/
    private Map<String,Object> baseUpdate(Map<String,Object> map){
        String id = MapUtil.getString(map,"id");
        if(StringUtil.isNull(id)){
            throw new BizException("传入人物ID为空");
        }
        VerificationUtil.verificationBase(map);
        Map<String,Object> queryMap = baseMapper.baseIdQuery(map);
        if(SetUtil.isMapNull(queryMap)){
            throw new BizException("修改失败，该人物不存在");
        }
        String name = MapUtil.getString(queryMap,"nickname");
        String nickname = MapUtil.getString(map,"nickname");
        if(!name.equals(nickname)){
            int count = baseMapper.baseIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，账号名称已存在");
            }
        }
        try{
            baseMapper.baseUpdate(map);
            String virtue = MapUtil.getString(map,"virtue");
            Map<String,Object> vMap = JSONObject.fromObject(virtue);
            if(!SetUtil.isMapNull(vMap)){
                vMap.put("character_id",MapUtil.getString(map,"id"));
                baseMapper.baseExtUpdate(vMap);
            }
            String weapon = MapUtil.getString(map,"weapon");
            Map<String,Object> wMap = JSONObject.fromObject(weapon);
            if(!SetUtil.isMapNull(wMap)){
                wMap.put("character_id",MapUtil.getString(map,"id"));
                baseMapper.baseAttUpdate(wMap);
            }
            Map<String,Object> potent = JsonUtil.formatJSON(MapUtil.getString(map,""));
            if(!SetUtil.isMapNull(potent)){
                potent.put("character_id",MapUtil.getString(map,"id"));
                baseMapper.basePotUpdate(potent);
            }
            return new HashMap<>();
        }catch (Exception e){
            e.printStackTrace();
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> baseDelete(Map<String,Object> map){
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());;
                baseMapper.baseDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*查看详情*/
    private Map<String,Object> baseSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"nickname");
        if(StringUtil.isNull(name)){
            throw new BizException("传入人物名称为空");
        }
        Map<String,Object> resultMap = baseMapper.baseEditQuery(map);
        String userId = MapUtil.getString(resultMap,"user_id");
        if(StringUtil.isNull(userId)){
            resultMap.put("userName","");
        }else{
            Map<String,Object> userMap = accountMapper.accountQueryName(userId);
            resultMap.put("userName",MapUtil.getString(userMap,"account"));
        }
        String schoolId = MapUtil.getString(resultMap,"school_id");
        if(StringUtil.isNull(schoolId)){
            resultMap.put("schoolName","");
        }else{
            Map<String,Object> schoolMap = schoolMapper.schoolQueryName(schoolId);
            resultMap.put("schoolName",MapUtil.getString(schoolMap,"name"));
        }
        String kongFu = Base64Util.Base64Decode(MapUtil.getString(resultMap,"kongfu_have_id"));
        if(StringUtil.isNull(kongFu)){
            resultMap.put("kongfu_have_id",new JSONArray());
        }else{
            JSONArray array = JSONArray.fromObject(kongFu);
            for(int i=0; i<array.size(); i++){
                JSONObject json = array.getJSONObject(i);
                Map<String,Object> resKF = kongFuMapper.kongFuQueryName(json.optString("id"));
                json.put("kfName",MapUtil.getString(resKF,"name"));
                json.put("kfType",MapUtil.getString(resKF,"type"));
            }
            resultMap.put("kongfu_have_id",array);
        }
        String baseId = MapUtil.getString(resultMap,"id");
        Map<String,Object> weapon = baseMapper.queryAttainmentsInfo(baseId);
        resultMap.put("weapon",SetUtil.clearValueNullToMap(weapon));
        Map<String,Object> virtue = baseMapper.queryBattleInfo(baseId);
        resultMap.put("virtue",SetUtil.clearValueNullToMap(virtue));
        Map<String,Object> potent = baseMapper.queryPotentialInfo(baseId);
        resultMap.put("potent",SetUtil.clearValueNullToMap(potent));
        return SetUtil.clearValueNullToMap(resultMap);
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
        List<Map<String,Object>> infoList = baseMapper.queryBaseInfo(userId);
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
                Map<String,Object> attainMap = baseMapper.queryAttainmentsInfo(baseId);
                result.put("attainInfo",SetUtil.clearValueNullToMap(attainMap));//造诣信息
                Map<String,Object> battleMap = baseMapper.queryBattleInfo(baseId);
                result.put("battleInfo",SetUtil.clearValueNullToMap(battleMap));//battle信息
                Map<String,Object> potentialMap = baseMapper.queryPotentialInfo(baseId);
                result.put("potentialInfo",SetUtil.clearValueNullToMap(potentialMap));//学识表
                List<Map<String,Object>> relationMap = baseMapper.queryRelationInfo(baseId);
                result.put("relationInfo",SetUtil.clearValueNullToList(relationMap));//关系表
                result.putAll(infoMap);
            }
        }
        return result;
    }
}
