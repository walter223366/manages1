package com.ral.manages.controller.manage;

import com.ral.manages.entity.manage.School;
import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.manage.ISchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/school")
public class SchoolController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolController.class);
    @Autowired
    private ISchoolService iSchoolService;

    /**分页查询*/
    @RequestMapping("/pagingQuery")
    public Object schoolPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**新增*/
    @RequestMapping("/schoolAdd")
    public Object schoolAdd(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolAdd(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**修改*/
    @RequestMapping("/schoolUpdate")
    public Object schoolUpdate(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolUpdate(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**删除*/
    @RequestMapping("/schoolDelete")
    public Object schoolDelete(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolDelete(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
