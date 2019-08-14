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

    /**
     * 分页查询
     * @param map map
     * @return Object
     */
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

    /**
     * 编辑查询
     * @param kongFu kongFu
     * @return Object
     */
    @PostMapping("/editQuery")
    public Object kongFuPagingQuery(KongFu kongFu){
        LOG.info("请求参数:" + kongFu);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuEditQuery(kongFu);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 招式管理选项查询
     * @return Object
     */
    @GetMapping("/kongFuQueryMove")
    public Object kongFuQueryMove(){
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuQueryMove();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }


    /**
     * 新增
     * @param kongFu kongFu
     * @return Object
     */
    @PostMapping("/inAdd")
    public Object kongFuAdd(KongFu kongFu){
        LOG.info("请求参数:" + kongFu);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuInsert(kongFu);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 修改
     * @param kongFu kongFu
     * @return Object
     */
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

    /**
     * 删除
     * @param kongFu kongFu
     * @return Object
     */
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

    /**
     * 批量删除
     * @param map map
     * @return Object
     */
    @PostMapping("/inBatchDelete")
    public Object kongFuBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iKongFuService.kongFuBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
