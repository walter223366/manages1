package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.emun.TableCode;
import com.ral.manages.entity.app.School;
import com.ral.manages.entity.Result;
import com.ral.manages.comms.verifi.VerificationParams;
import com.ral.manages.mapper.app.ISchoolMapper;
import com.ral.manages.service.app.ISchoolService;
import com.ral.manages.comms.page.PageBean;
import com.ral.manages.util.Base64Util;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SchoolServiceImpl implements ISchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolServiceImpl.class);
    @Autowired
    private ISchoolMapper iSchoolMapper;

    /**
     * 分页查询
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result schoolPagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map), PageBean.pageSize(map));
        List<Map<String,Object>> schoolList = iSchoolMapper.schoolPagingQuery(map);
        return Result.success(ResultCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),schoolList));
    }

    /**
     * 编辑查询
     *
     * @param school school
     * @return GeneralResponse
     */
    @Override
    public Result schoolEditQuery(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> result = iSchoolMapper.schoolEditQuery(school);
        return Result.success(ResultCode.SUCCESS.getMsg(),result);
    }

    /**
     * 新增
     *
     * @param school school
     * @return GeneralResponse
     */
    @Override
    public Result schoolInsert(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iSchoolMapper.schoolIsExist(school);
        if(count > 0){
            return Result.fail("新增失败，门派名称已存在");
        }
        school.setSchool_id(StringUtil.getUUID());
        school.setDeleteStatus(TableCode.Del.DELETE_ZERO.getCode());
        try{
            iSchoolMapper.schoolInsert(school);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     *
     * @param  school school
     * @return GeneralResponse
     */
    @Override
    public Result schoolUpdate(School school) {
        if(StringUtil.isNull(school.getSchool_id())){
            return Result.fail("传入门派ID为空");
        }
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        Map<String,Object> map = iSchoolMapper.schoolIdQuery(school);
        if(SetUtil.isMapNull(map)){
            return Result.fail("修改失败，该门派不存在");
        }
        String name = SetUtil.toMapValueString(map,"name");
        if(!name.equals(school.getName())){
            int count = iSchoolMapper.schoolIsExist(school);
            if(count > 0){
                return Result.fail("修改失败，门派名称已存在");
            }
        }
        try{
            iSchoolMapper.schoolUpdate(school);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e) {
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param school school
     * @return GeneralResponse
     */
    @Override
    public Result schoolDelete(School school) {
        String msg = VerificationParams.verificationSchool(school);
        if(!StringUtil.isNull(msg)){
            return Result.fail(msg);
        }
        int count = iSchoolMapper.schoolIsExist(school);
        if(count <= 0){
            return Result.fail("删除失败，该门派不存在");
        }
        school.setDeleteStatus(TableCode.Del.DELETE_ONE.getCode());
        try{
            iSchoolMapper.schoolDelete(school);
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e) {
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 批量删除
     *
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public Result schoolBatchDelete(Map<String,Object> map) {
        List<Map<String,Object>> resluList = new ArrayList<Map<String,Object>>();
        String data = Base64Util.Base64Decode(SetUtil.toMapValueString(map,"data"));
        try{
            resluList = JSONArray.fromObject(data);
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail("传入data参数JSON格式错误");
        }
        if(SetUtil.isListNull(resluList)){
            return Result.fail("传入data参数为空");
        }
        try{
            for(Map<String,Object> upMap : resluList){
                upMap.put("deleteStatus", TableCode.Del.DELETE_ONE.getCode());
                iSchoolMapper.schoolBatchDelete(upMap);
            }
            return Result.successNotdatas(ResultCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResultCode.FAIL.getMsg()+e.getMessage(),e);
            return Result.fail(ResultCode.FAIL.getMsg()+e.getMessage());
        }
    }
}
