package com.ral.manages.controller.manage;

import com.ral.manages.exception.GeneralResponse;
import com.ral.manages.service.manage.IEffectService;
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
            generalResponse = GeneralResponse.error("系统错误"+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
