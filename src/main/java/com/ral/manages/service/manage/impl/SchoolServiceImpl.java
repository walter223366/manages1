package com.ral.manages.service.manage.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.entity.manage.School;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.exception.VerificationParams;
import com.ral.manages.mapper.manage.ISchoolMapper;
import com.ral.manages.service.manage.ISchoolService;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements ISchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolServiceImpl.class);

    @Autowired
    private ISchoolMapper iSchoolMapper;

    /**分页查询*/
    @Override
    public GeneralResponse schoolPagingQuery(Map<String,Object> map) {
        GeneralResponse generalResponse = new GeneralResponse();
        int pageNum = SetUtil.toMapValueInt(map,"pageNum");
        int pageSize = SetUtil.toMapValueInt(map,"pageSize");
        pageNum = (pageNum==0?1:pageNum);
        pageSize = (pageSize==0?10:pageSize);
        Page<Map<String,Object>> page = PageHelper.startPage(pageNum,pageSize);
        List<Map<String,Object>> schoolList = iSchoolMapper.selectSchoolPagingQuery(map);
        Map<String,Object> result = new HashMap<>();
        result.put("datas",schoolList);
        result.put("total",page.getTotal());
        return generalResponse.success("操作成功",result);
    }

    /**新增*/
    @Override
    public GeneralResponse schoolAdd(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iSchoolMapper.selectSchoolToExist(school);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该门派名称已存在");
        }
        school.setSchool_id(StringUtil.getUUID());
        try{
            iSchoolMapper.insertSchool(school);
            return GeneralResponse.successNotdatas("新增成功");
        }catch (Exception e){
            LOG.debug("新增失败，"+e.getMessage(),e);
            return GeneralResponse.fail("新增失败，"+e.getMessage());
        }
    }

    /**修改*/
    @Override
    public GeneralResponse schoolUpdate(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iSchoolMapper.selectSchoolToExist(school);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该门派名称不存在");
        }
        try{
            iSchoolMapper.updateSchool(school);
            return GeneralResponse.successNotdatas("修改成功");
        }catch (Exception e) {
            LOG.debug("修改失败，"+e.getMessage(),e);
            return GeneralResponse.fail("修改失败，"+e.getMessage());
        }
    }

    /**删除*/
    @Override
    public GeneralResponse schoolDelete(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iSchoolMapper.selectSchoolToExist(school);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该门派名称不存在");
        }
        school.setDel_status(1);
        try{
            iSchoolMapper.deleteSchool(school);
            return GeneralResponse.successNotdatas("删除成功");
        }catch (Exception e) {
            LOG.debug("删除失败，"+e.getMessage(),e);
            return GeneralResponse.fail("删除失败，"+e.getMessage());
        }
    }
}
