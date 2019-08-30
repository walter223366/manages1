package com.ral.manages.controller.app;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.entity.Result;
import com.ral.manages.entity.app.Effect;
import com.ral.manages.service.app.IEffectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/effect/")
public class EffectController {

    private static final Logger LOG = LoggerFactory.getLogger(EffectController.class);
    @Autowired
    private IEffectService iEffectService;

    //分页查询
    @PostMapping("pagingQuery")
    public Object effectPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //编辑查询
    @PostMapping("editQuery")
    public Object effectEditQuery(@RequestBody Effect effect){
        LOG.info("请求参数:" + effect);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectEditQuery(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //新增
    @PostMapping("inAdd")
    public Object effectInsert(@RequestBody Effect effect){
        LOG.info("请求参数:" + effect);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectInsert(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //修改
    @PostMapping("inUpdate")
    public Object effectUpdate(@RequestBody Effect effect){
        LOG.info("请求参数:" + effect);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectUpdate(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //删除
    @PostMapping("inDelete")
    public Object effectDelete(@RequestBody Effect effect){
        LOG.info("请求参数:" + effect);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectDelete(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //批量删除
    @PostMapping("inBatchDelete")
    public Object effectBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        Result generalResponse = new Result();
        try{
            generalResponse = iEffectService.effectBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
