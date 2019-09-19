package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.mapper.app.IAccountMapper;
import com.ral.manages.mapper.app.IBaseNPCMapper;
import com.ral.manages.mapper.app.ISchoolMapper;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import com.ral.manages.util.VerificationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseNpc")
public class BaseNPCServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(BaseNPCServiceImpl.class);
    @Autowired
    private IBaseNPCMapper baseNPCMapper;
    @Autowired
    private ISchoolMapper schoolMapper;
    @Autowired
    private IAccountMapper accountMapper;

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
            case ProjectConst.PAGINGQUERY: result = baseNPCPagingQuery(map);
                break;
            case ProjectConst.EDITQUERY: result = baseNPCEditQuery(map);
                break;
            case ProjectConst.SEEDETAILS: result = baseNPCSee(map);
                break;
            case ProjectConst.INSERT: result = baseNPCInsert(map);
                break;
            case ProjectConst.UPDATE: result = baseNPCUpdate(map);
                break;
            case ProjectConst.DELETE: result = baseNPCDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> baseNPCPagingQuery(Map<String,Object> map){
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> baseList = baseNPCMapper.baseNpcPagingQuery(map);
        for(Map<String,Object> baseMap : baseList){
            int sex = MapUtil.getInt(baseMap,"sex");
            String sexValue = (sex== TableCode.SEX_ZERO.getCode()?TableCode.SEX_ZERO.getName():TableCode.SEX_ONE.getName());
            baseMap.put("sex",sexValue);
            String schoolId = MapUtil.getString(baseMap,"school_id");
            if(!StringUtil.isNull(schoolId)){
                Map<String,Object> shcoolMap = schoolMapper.schoolIdQuery(baseMap);
                baseMap.put("schoolName",MapUtil.getString(shcoolMap,"name"));
            }
        }
        return PageBean.resultPage(page.getTotal(),baseList);
    }

    /*编辑查询*/
    private Map<String,Object> baseNPCEditQuery(Map<String,Object> map) {
        String nickname = MapUtil.getString(map,"nickname");
        if(StringUtil.isNull(nickname)){
            throw new BizException("传入人物名称为空");
        }
        return baseNPCMapper.baseNpcEditQuery(map);
    }

    /*新增*/
    private Map<String,Object> baseNPCInsert(Map<String,Object> map){
        VerificationUtil.verificationBaseNpc(map);
        int count = baseNPCMapper.baseNpcIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，人物名称已存在");
        }
        map.put("user_id","b936e068b53f43feac2dd55b4a4c5ed8");//TODO 暂固定
        map.put("cancellation",TableCode.CANCELLATION_ONE.getCode());
        map.put("id",StringUtil.getUUID());
        try{
            baseNPCMapper.baseNPCInsert(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> baseNPCUpdate(Map<String,Object> map){
        String id = MapUtil.getString(map,"id");
        if(StringUtil.isNull(id)){
            throw new BizException("传入人物ID为空");
        }
        VerificationUtil.verificationBaseNpc(map);
        Map<String,Object> queryMap = baseNPCMapper.baseNpcIdQuery(map);
        if(SetUtil.isMapNull(queryMap)){
            throw new BizException("修改失败，该人物不存在");
        }
        String name = MapUtil.getString(queryMap,"nickname");
        String nickname = MapUtil.getString(map,"nickname");
        if(!name.equals(nickname)){
            int count = baseNPCMapper.baseNpcIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，账号名称已存在");
            }
        }
        try{
            baseNPCMapper.baseNPCUpdate(map);
            return new HashMap<>();
        }catch (Exception e){
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }


    /*删除*/
    private Map<String,Object> baseNPCDelete(Map<String,Object> map){
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());;
                baseNPCMapper.baseNPCDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*查看详情*/
    private Map<String,Object> baseNPCSee(Map<String,Object> map) {
        String name = MapUtil.getString(map,"nickname");
        if(StringUtil.isNull(name)){
            throw new BizException("传入人物名称为空");
        }
        Map<String,Object> resultMap = baseNPCMapper.baseNpcEditQuery(map);
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
        return resultMap;
    }
}
