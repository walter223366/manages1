package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.IEffectMapper;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("move")
public class MoveServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(MoveServiceImpl.class);
    @Autowired
    private IMoveMapper iMoveMapper;
    @Autowired
    private IKongFuMapper iKongFuMapper;
    @Autowired
    private IEffectMapper iEffectMapper;

    /**
     * 处理招式管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = movePagingQuery(map);
                break;
            case ProjectConst.EDITQUERY: result = moveEditQuery(map);
                break;
            case ProjectConst.INSERT: result = moveInsert(map);
                break;
            case ProjectConst.UPDATE: result = moveUpdate(map);
                break;
            case ProjectConst.DELETE: result = moveDelete(map);
                break;
            case ProjectConst.BATCHDELETE: result = moveBatchDelete(map);
                break;
            case ProjectConst.MEFFECTBOX: result = moveAddEffect();
                break;
            case ProjectConst.MKONGFUBOX: result = moveAddKongFu();
                break;
            case ProjectConst.SEEDETAILS: result = moveSee(map);
            default:
                throw new BizException("传入该方法不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> movePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> moveList = iMoveMapper.movePagingQuery(map);
        for(Map<String,Object> moveMap : moveList){
            String kongFuId = SetUtil.toMapValueString(moveMap,"kongfu_id");
            if(StringUtil.isNull(kongFuId)){
                moveMap.put("kongFuName","");
            }else{
                moveMap.put("kongFuName",seeKongFuName(kongFuId));
            }
        }
        return PageBean.resultPage(page.getTotal(),moveList);
    }

    /*编辑查询*/
    private Map<String,Object> moveEditQuery(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入招式名称为空");
        }
        return iMoveMapper.moveEditQuery(map);
    }

    /*新增*/
    private Map<String,Object> moveInsert(Map<String,Object> map) {
        VerificationUtil.verificationMove(map);
        int count = iMoveMapper.moveIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，招式名称已存在");
        }
        map.put("zhaoshi_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            iMoveMapper.moveInsert(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> moveUpdate(Map<String,Object> map) {
        String zhaoshi_id = MapUtil.getString(map,"zhaoshi_id");
        if(StringUtil.isNull(zhaoshi_id)){
            throw new BizException("传入招式ID为空");
        }
        VerificationUtil.verificationMove(map);
        Map<String,Object> qMap = iMoveMapper.moveIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("修改失败，该招式不存在");
        }
        String name = MapUtil.getString(qMap,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = iMoveMapper.moveIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，招式名称已存在");
            }
        }
        try{
            iMoveMapper.moveUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> moveDelete(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入招式名称为空");
        }
        int count = iMoveMapper.moveIsExist(map);
        if(count <= 0){
            throw new BizException("删除失败，该招式不存在");
        }
        map.put("deleteStatus",TableCode.DELETE_ONE.getCode());
        try{
            iMoveMapper.moveDelete(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*批量删除*/
    private Map<String,Object> moveBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                iMoveMapper.moveDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("批量删除失败："+e.getMessage());
            throw new BizException("批量删除失败："+e.getMessage());
        }
    }

    /*效果下拉框*/
    private Map<String,Object> moveAddEffect() {
        List<Map<String,Object>> resultList = iEffectMapper.effectQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*功夫下拉框*/
    private Map<String,Object> moveAddKongFu() {
        List<Map<String,Object>> resultList = iKongFuMapper.kongFuQueryMarquee();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("data",resultList);
        return resultMap;
    }

    /*查看详情*/
    private Map<String,Object> moveSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入招式名称为空");
        }
        Map<String,Object> resultMap = iMoveMapper.moveEditQuery(map);
        String kongFuId = SetUtil.toMapValueString(resultMap,"kongfu_id");
        if(StringUtil.isNull(kongFuId)){
            resultMap.put("kongFuName","");
        }else{
            resultMap.put("kongFuName",seeKongFuName(kongFuId));
        }
        String effectId = SetUtil.toMapValueString(resultMap,"zhaoshi_effect");
        if(StringUtil.isNull(effectId)){
            resultMap.put("effectName","");
        }else{
            resultMap.put("effectName",seeEffectName(effectId));
        }
        return resultMap;
    }

    /*获取功夫名称*/
    private List<String> seeKongFuName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("kIds",list);
        List<Map<String,Object>> resultList = iKongFuMapper.kongFuQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }

    /*获取效果名称*/
    private List<String> seeEffectName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("eIds",list);
        List<Map<String,Object>> resultList = iEffectMapper.effectQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }
}
