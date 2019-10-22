package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.ProjectConst;
import com.ral.manages.service.app.UnifiedCall;
import com.ral.manages.util.*;
import com.ral.manages.mapper.app.ISchoolMapper;
import com.ral.manages.comms.page.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("school")
public class SchoolServiceImpl implements UnifiedCall {

    private static final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);
    @Autowired
    private ISchoolMapper schoolMapper;

    /**
     * 处理门派管理
     *
     * @param method method
     * @param map map
     * @return map
     */
    @Override
    public Map<String,Object> uCall(String method,Map<String,Object> map) {
        Map<String,Object> result = new HashMap<String,Object>();
        switch (method){
            case ProjectConst.PAGINGQUERY: result = schoolPagingQuery(map);
                break;
            case ProjectConst.SEEQUERY: result = schoolSee(map);
                break;
            case ProjectConst.INSERT: result = schoolInsert(map);
                break;
            case ProjectConst.UPDATE: result = schoolUpdate(map);
                break;
            case ProjectConst.DELETE: result = schoolDelete(map);
                break;
            default:
                throw new BizException("传入方法名不存在");
        }
        return result;
    }

    /*分页查询*/
    private Map<String,Object> schoolPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> schoolList = schoolMapper.schoolPagingQuery(map);
        return PageBean.resultPage(page.getTotal(),schoolList);
    }

    /*新增*/
    private Map<String,Object> schoolInsert(Map<String,Object> map) {
        VerificationUtil.verificationSchool(map);
        int count = schoolMapper.schoolIsExist(map);
        if(count > 0){
            throw new BizException("新增失败，门派名称已存在");
        }
        map.put("school_id",StringUtil.getUUID());
        map.put("deleteStatus",TableCode.DELETE_ZERO.getCode());
        try{
            schoolMapper.schoolInsert(SetUtil.turnNull(map));
            return new HashMap<>();
        }catch (Exception e){
            log.debug("新增失败："+e.getMessage());
            throw new BizException("新增失败："+e.getMessage());
        }
    }

    /*修改*/
    private Map<String,Object> schoolUpdate(Map<String,Object> map) {
        String school_id = MapUtil.getString(map,"school_id");
        if(StringUtil.isNull(school_id)){
            throw new BizException("传入门派ID为空");
        }
        VerificationUtil.verificationSchool(map);
        Map<String,Object> qMap = schoolMapper.schoolIdQuery(map);
        if(SetUtil.isMapNull(qMap)){
           throw new BizException("修改失败，该门派不存在");
        }
        String name = MapUtil.getString(qMap,"name");
        String cName = MapUtil.getString(map,"name");
        if(!name.equals(cName)){
            int count = schoolMapper.schoolIsExist(map);
            if(count > 0){
                throw new BizException("修改失败，门派名称已存在");
            }
        }
        try{
            schoolMapper.schoolUpdate(SetUtil.turnNull(map));
            return new HashMap<>();
        }catch (Exception e) {
            log.debug("修改失败："+e.getMessage());
            throw new BizException("修改失败："+e.getMessage());
        }
    }

    /*删除*/
    private Map<String,Object> schoolDelete(Map<String,Object> map) {
        List<Map<String,Object>> dataList = (List<Map<String,Object>>) map.get("data");
        if(SetUtil.isListNull(dataList)){
            throw new BizException("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : dataList){
                upMap.put("deleteStatus", TableCode.DELETE_ONE.getCode());
                schoolMapper.schoolDelete(upMap);
            }
            return new HashMap<>();
        }catch (Exception e){
            log.debug("删除失败："+e.getMessage());
            throw new BizException("删除失败："+e.getMessage());
        }
    }

    /*详情*/
    private Map<String,Object> schoolSee(Map<String,Object> map) {
        VerificationUtil.verificationSchool(map);
        return schoolMapper.schoolEditQuery(map);
    }
}
