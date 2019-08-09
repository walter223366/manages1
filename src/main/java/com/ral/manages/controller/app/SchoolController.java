package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.entity.app.School;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.service.app.ISchoolService;
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

    /**
     * 分页查询
     * @param map map
     * @return Object
     */
    @RequestMapping("/pagingQuery")
    public Object schoolPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 编辑查询
     * @param school school
     * @return Object
     */
    @RequestMapping("/editQuery")
    public Object schoolEditQuery(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolEditQuery(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 新增
     * @param school school
     * @return Object
     */
    @RequestMapping("/inAdd")
    public Object schoolInsert(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolInsert(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 修改
     * @param school school
     * @return Object
     */
    @RequestMapping("/inUpdate")
    public Object schoolUpdate(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolUpdate(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 删除
     * @param school school
     * @return Object
     */
    @RequestMapping("/inDelete")
    public Object schoolDelete(School school){
        LOG.info("请求参数:" + school);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolDelete(school);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 批量删除
     * @param map map
     * @return Object
     */
    @RequestMapping("/inBatchDelete")
    public Object schoolBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iSchoolService.schoolBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
