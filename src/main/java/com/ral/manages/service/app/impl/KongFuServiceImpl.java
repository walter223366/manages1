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
    private IKongFuMapper iKongFuMapper;
    @Autowired
    private IMoveMapper iMoveMapper;

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
            case ProjectConst.EDITQUERY: result = kongFuEditQuery(map);
                break;
            case ProjectConst.INSERT: result = kongFuInsert(map);
                break;
            case ProjectConst.UPDATE: result = kongFuUpdate(map);
                break;
            case ProjectConst.DELETE: result = kongFuDelete(map);
                break;
            case ProjectConst.BATCHDELETE: result = kongFuBatchDelete(map);
                break;
            case ProjectConst.KMOVEBOX: result = kongFuAddMove();
                break;
            case ProjectConst.SEEDETAILS: result = kongFuSee(map);
                break;
            default:
                throw new BizException("传入该方法不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> kongFuPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> kongFuList = iKongFuMapper.kongFuPagingQuery(map);
        for(Map<String,Object> kongFuMap : kongFuList){
            kongFuMap.put("type",kongFuType(SetUtil.toMapValueInt(kongFuMap,"type")));
            int enable = SetUtil.toMapValueInt(kongFuMap,"enable");
            String enableValue = (enable== TableCode.ENABLE_ONE.getCode()? TableCode.ENABLE_ONE.getName(): TableCode.ENABLE_ZERO.getName());
            kongFuMap.put("enable",enableValue);
        }
        return PageBean.resultPage(page.getTotal(),kongFuList);
    }

    /*编辑查询*/
    private Map<String,Object> kongFuEditQuery(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
           throw new BizException("传入功夫名称为空");
        }
        return iKongFuMapper.kongFuEditQuery(map);
    }

    /*招式下拉框*/
    private Map<String,Object> kongFuAddMove() {
        List<Map<String,Object>> kongFuList = iMoveMapper.moveQueryMarquee();
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("data",kongFuList);
        return result;
    }

    /*新增*/
    private Map<String,Object> kongFuInsert(Map<String,Object> map) {
        VerificationUtil.verificationKongFu(map);
        int count = iKongFuMapper.kongFuIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，功夫名称已存在");
        }
        map.put("kongfu_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            iKongFuMapper.kongFuInsert(map);
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
        Map<String,Object> qMap = iKongFuMapper.kongFuIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
            throw new BizException("修改失败，该功夫不存在");
        }
        String name =  MapUtil.getString(qMap,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = iKongFuMapper.kongFuIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，功夫名称已存在");
            }
        }
        try{
            iKongFuMapper.kongFuUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> kongFuDelete(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入功夫名称为空");
        }
        int count = iKongFuMapper.kongFuIsExist(map);
        if(count <= 0){
            throw new BizException("删除失败，该功夫不存在");
        }
        map.put("deleteStatus",TableCode.DELETE_ONE.getCode());
        try{
            iKongFuMapper.kongFuDelete(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*批量删除*/
    private Map<String,Object> kongFuBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                iKongFuMapper.kongFuDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("批量删除失败："+e.getMessage());
            throw new BizException("批量删除失败："+e.getMessage());
        }
    }

    /*查看详情*/
    private Map<String,Object> kongFuSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"name");
        if(StringUtil.isNull(name)){
            throw new BizException("传入功夫名称为空");
        }
        Map<String,Object> resultMap = iKongFuMapper.kongFuEditQuery(map);
        String moveId = MapUtil.getString(resultMap,"kongfu_zhaoshi");
        if(StringUtil.isNull(moveId)){
            resultMap.put("moveName","");
        }else{
            resultMap.put("moveName",seeMoveName(moveId));
        }
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
        List<Map<String,Object>> resultList = iMoveMapper.moveQueryMarqueeName(result);
        List<String> nameList = new ArrayList<String>();
        for(Map<String,Object> map : resultList){
            nameList.add(SetUtil.toMapValueString(map,"name"));
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
}
