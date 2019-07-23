package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Effect;
import com.ral.manages.service.app.IEffectService;
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
@RequestMapping("/effect")
public class EffectController {

    private static final Logger LOG = LoggerFactory.getLogger(EffectController.class);
    @Autowired
    private IEffectService iEffectService;

    /**分页查询*/
    @RequestMapping("/pagingQuery")
    public Object effectPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iEffectService.effectPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**新增*/
    @RequestMapping("/effectAdd")
    public Object effectAdd(Effect effect){
        LOG.info("请求参数:" + effect);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iEffectService.effectAdd(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**修改*/
    @RequestMapping("/effectUpdate")
    public Object effectUpdate(Effect effect){
        LOG.info("请求参数:" + effect);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iEffectService.effectUpdate(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**删除*/
    @RequestMapping("/effectDelete")
    public Object effectDelete(Effect effect){
        LOG.info("请求参数:" + effect);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iEffectService.effectDelete(effect);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
