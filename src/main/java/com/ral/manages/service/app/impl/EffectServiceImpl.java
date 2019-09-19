package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.IEffectMapper;
import com.ral.manages.comms.page.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("effect")
public class EffectServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(EffectServiceImpl.class);
    @Autowired
    private IEffectMapper effectMapper;

    /**
     * 处理效果管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = effectPagingQuery(map);
                break;
            case ProjectConst.EDITQUERY: result = effectEditQuery(map);
                break;
            case ProjectConst.INSERT: result = effectInsert(map);
                break;
            case ProjectConst.UPDATE: result = effectUpdate(map);
                break;
            case ProjectConst.DELETE: result = effectDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> effectPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> effectList = effectMapper.effectPagingQuery(map);
        for(Map<String,Object> effectMap : effectList){
            int target = SetUtil.toMapValueInt(effectMap,"target");
            String target_value = (target==0? TableCode.TARGET_ZERO.getName(): TableCode.TARGET_ONE.getName());
            effectMap.put("target",target_value);
        }
        return PageBean.resultPage(page.getTotal(),effectList);
    }

    /*编辑查询*/
    private Map<String,Object> effectEditQuery(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入效果名称错误");
        }
        return effectMapper.effectEditQuery(map);
    }

    /*新增*/
    private Map<String,Object> effectInsert(Map<String,Object> map) {
        VerificationUtil.verificationEffect(map);
        int count = effectMapper.effectIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，效果名称已存在");
        }
        map.put("effect_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            effectMapper.effectInsert(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> effectUpdate(Map<String,Object> map) {
        String effect_id = MapUtil.getString(map,"effect_id");
        if(StringUtil.isNull(effect_id)){
            throw new BizException("传入效果ID为空");
        }
        VerificationUtil.verificationEffect(map);
        Map<String,Object> qMap = effectMapper.effectIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("修改失败，该效果不存在");
        }
        String name = MapUtil.getString(map,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = effectMapper.effectIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，效果名称已存在");
            }
        }
        try{
            effectMapper.effectUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> effectDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                effectMapper.effectDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }
}
