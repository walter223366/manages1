package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.emun.StateTable;
import com.ral.manages.entity.app.School;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.mapper.manage.ISchoolMapper;
import com.ral.manages.service.app.ISchoolService;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> schoolList = iSchoolMapper.selectSchoolPagingQuery(map);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(), PageBean.resultPage(page.getTotal(),schoolList));
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
        school.setCancellation(StateTable.School.CANCELLATION_ZERO.getCode());
        try{
            iSchoolMapper.insertSchool(school);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
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
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e) {
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
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
        school.setCancellation(StateTable.School.CANCELLATION_ONE.getCode());
        try{
            iSchoolMapper.deleteSchool(school);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e) {
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
