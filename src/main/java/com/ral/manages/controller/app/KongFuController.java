package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.entity.app.KongFu;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.service.app.IKongFuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/kongFu")
public class KongFuController {

    private static final Logger LOG = LoggerFactory.getLogger(KongFuController.class);
    @Autowired
    private IKongFuService iKongFuService;

    /**分页查询*/
    @PostMapping("/pagingQuery")
    public Object kongFuPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**招式管理选项查询*/
    @GetMapping("/kongFuOrZhaoShi")
    public Object kongFuOrZhaoShi(){
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuOrZhaoShi();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }


    /**新增*/
    @PostMapping("/inAdd")
    public Object kongFuAdd(KongFu kongFu){
        LOG.info("请求参数:" + kongFu);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuAdd(kongFu);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**修改*/
    @PostMapping("/inUpdate")
    public Object kongFuUpdate(KongFu kongFu){
        LOG.info("请求参数:" + kongFu);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuUpdate(kongFu);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**删除*/
    @PostMapping("/inDelete")
    public Object kongFuDelete(KongFu kongFu){
        LOG.info("请求参数:" + kongFu);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuDelete(kongFu);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

}
