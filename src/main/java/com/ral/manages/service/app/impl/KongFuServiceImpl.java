package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.comms.page.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("kongFu")
public class KongFuServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(KongFuServiceImpl.class);
    @Autowired
    private IKongFuMapper kongFuMapper;
    @Autowired
    private IMoveMapper moveMapper;

    /**
     * 处理功夫管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = kongFuPagingQuery(map);
                break;
            case ProjectConst.SEEQUERY: result =  kongFuSee(map);
                break;
            case ProjectConst.INSERT: result = kongFuInsert(map);
                break;
            case ProjectConst.UPDATE: result = kongFuUpdate(map);
                break;
            case ProjectConst.DELETE: result = kongFuDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> kongFuList = kongFuMapper.kongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("typeValue",kongFuType(MapUtil.getInt(kongFuMap,"type")));
            int enable = MapUtil.getInt(kongFuMap,"enable");
            String enableValue = (enable== TableCode.ENABLE_ONE.getCode()? TableCode.ENABLE_ONE.getName(): TableCode.ENABLE_ZERO.getName());
            kongFuMap.put("enableValue",enableValue);
        }
        return PageBean.resultPage(page.getTotal(),kongFuList);
    }

    /*新增*/
    private Map<String,Object> kongFuInsert(Map<String,Object> map) {
        VerificationUtil.verificationKongFu(map);
        int count = kongFuMapper.kongFuIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，功夫名称已存在");
        }
        String id = StringUtil.getUUID();
        map.put("kongfu_id",id);
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            kongFuMapper.kongFuInsert(SetUtil.turnNull(map));
            String kMove = MapUtil.getString(map,"kongfu_zhaoshi");
            if(!StringUtil.isNull(kMove)){
                String[] moveIds = kMove.split(",");
                for(int i=0; i<moveIds.length; i++){
                    Map<String,Object> moveMap = new HashMap<>();
                    moveMap.put("moveId",moveIds[i]);
                    moveMap.put("kongfu_id",id);
                    moveMapper.moveUpdateKF(moveMap);
                }
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> kongFuUpdate(Map<String,Object> map) {
        String kongfu_id = MapUtil.getString(map,"kongfu_id");
        if(StringUtil.isNull(kongfu_id)){
            throw new BizException("传入功夫ID为空");
        }
        VerificationUtil.verificationKongFu(map);
        Map<String,Object> qMap = kongFuMapper.kongFuIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("修改失败，该功夫不存在");
        }
        String name =  MapUtil.getString(qMap,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = kongFuMapper.kongFuIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，功夫名称已存在");
            }
        }
        try{
            kongFuMapper.kongFuUpdate(SetUtil.turnNull(map));
            List<Map<String,Object>> moveIds = getUnseMoveId(qMap,map);
            for(Map<String,Object> moveId: moveIds){
                moveMapper.moveUpdateKF(moveId);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> kongFuDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                kongFuMapper.kongFuDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*详情*/
    private Map<String,Object> kongFuSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入功夫名称为空");
        }
        Map<String,Object> resultMap = kongFuMapper.kongFuEditQuery(map);
        String moveId = MapUtil.getString(resultMap,"kongfu_zhaoshi");
        if(StringUtil.isNull(moveId)){
            resultMap.put("moveName","");
        }else{
            resultMap.put("moveName",seeMoveName(moveId));
        }
        int type = MapUtil.getInt(resultMap,"type");
        resultMap.put("typeValue",kongFuType(type));
        int enable = MapUtil.getInt(resultMap,"enable");
        String enableValue = (enable== TableCode.ENABLE_ONE.getCode()? TableCode.ENABLE_ONE.getName(): TableCode.ENABLE_ZERO.getName());
        resultMap.put("enableValue",enableValue);
        return resultMap;
    }

    /*获取招式名称*/
    private List<String> seeMoveName(String str){
        String[] values = str.split(",");
        List<String> list = new ArrayList<String>();
        for(String value : values){
            list.add(value);
        }
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("mIds",list);
        List<Map<String,Object>> resultList = moveMapper.moveQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(MapUtil.getString(map,"name"));
        }
        return nameList;
    }

   /*处理功夫类型*/
    private String kongFuType(int type){
        switch (type){
            case 0:return TableCode.TYPE_ZERO.getName();
            case 1:return TableCode.TYPE_ONE.getName();
            case 2:return TableCode.TYPE_TWO.getName();
            case 3:return TableCode.TYPE_THREE.getName();
            case 4:return TableCode.TYPE_FOUR.getName();
            case 5:return TableCode.TYPE_FIVES.getName();
            case 6:return TableCode.TYPE_SIX.getName();
            case 7:return TableCode.TYPE_SEVEN.getName();
            default:return "其他";
        }
    }

    private List<Map<String,Object>> getUnseMoveId(Map<String,Object> qMap,Map<String,Object> map){
        String[] moveIds = MapUtil.getString(qMap,"kongfu_zhaoshi").split(",");
        String[] zhaoIds = MapUtil.getString(map,"kongfu_zhaoshi").split(",");
        for(int i=0; i<moveIds.length; i++){

        }
        return null;
    }

}
