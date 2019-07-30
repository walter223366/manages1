package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.ZhaoShi;
import com.ral.manages.service.app.IZhaoShiService;
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
@RequestMapping("/zhaoShi")
public class ZhaoShiController {

    private static final Logger LOG = LoggerFactory.getLogger(ZhaoShiController.class);
    @Autowired
    private IZhaoShiService iZhaoShiService;


    /**分页查询*/
    @RequestMapping("/pagingQuery")
    public Object accountPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iZhaoShiService.zhaoShiPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**新增*/
    @RequestMapping("inAdd")
    public Object accountAdd(ZhaoShi zhaoShi){
        LOG.info("请求参数:" + zhaoShi);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iZhaoShiService.zhaoShiAdd(zhaoShi);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**修改*/
    @RequestMapping("inUpdate")
    public Object accountUpdate(ZhaoShi zhaoShi){
        LOG.info("请求参数:" + zhaoShi);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iZhaoShiService.zhaoShiUpdate(zhaoShi);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**删除*/
    @RequestMapping("inDelete")
    public Object accountDelete(ZhaoShi zhaoShi){
        LOG.info("请求参数:" + zhaoShi);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iZhaoShiService.zhaoShiDelete(zhaoShi);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
