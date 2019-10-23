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

import java.util.*;

@Service("move")
public class MoveServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(MoveServiceImpl.class);
    @Autowired
    private IMoveMapper moveMapper;
    @Autowired
    private IKongFuMapper kongFuMapper;
    @Autowired
    private IEffectMapper effectMapper;

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
            case ProjectConst.SEEQUERY: result = moveSee(map);
                break;
            case ProjectConst.INSERT: result = moveInsert(map);
                break;
            case ProjectConst.UPDATE: result = moveUpdate(map);
                break;
            case ProjectConst.DELETE: result = moveDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> movePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> moveList = moveMapper.movePagingQuery(map);
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

    /*新增*/
    private Map<String,Object> moveInsert(Map<String,Object> map) {
        VerificationUtil.verificationMove(map);
        int count = moveMapper.moveIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，招式名称已存在");
        }
        map.put("zhaoshi_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            moveMapper.moveInsert(SetUtil.turnNull(map));
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
        Map<String,Object> qMap = moveMapper.moveIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("修改失败，该招式不存在");
        }
        String name = MapUtil.getString(qMap,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = moveMapper.moveIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，招式名称已存在");
            }
        }
        try{
            moveMapper.moveUpdate(SetUtil.turnNull(map));
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> moveDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                moveMapper.moveDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*详情*/
    private Map<String,Object> moveSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入招式名称为空");
        }
        Map<String,Object> resultMap = moveMapper.moveEditQuery(map);
        String kongFuId = MapUtil.getString(resultMap,"kongfu_id");
        if(StringUtil.isNull(kongFuId)){
            resultMap.put("kongFuName","");
        }else{
            Map<String,Object> seeMap = kongFuMapper.kongFuQueryName(kongFuId);
            resultMap.put("kongFuName",MapUtil.getString(seeMap,"name"));
        }
        String effectId =  MapUtil.getString(resultMap,"zhaoshi_effect");
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
        List<Map<String,Object>> resultList = kongFuMapper.kongFuQueryMarqueeName(result);
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
        List<Map<String,Object>> resultList = effectMapper.effectQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
        }
        return nameList;
    }

    private String spliceString(Map<String,Object> map){
        int yellowSend = MapUtil.getInt(map,"yellowSpend");
        int goldSpend = MapUtil.getInt(map,"goldSpend");
        int greenSpend = MapUtil.getInt(map,"greenSpend");
        int blueSpend = MapUtil.getInt(map,"blueSpend");
        int purpleSpend = MapUtil.getInt(map,"purpleSpend");
        return yellowSend+","+goldSpend+"."+greenSpend+","+blueSpend+","+purpleSpend;
    }
}
