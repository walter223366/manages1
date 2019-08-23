package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.entity.app.Account;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.service.app.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/account/")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IAccountService iAccountService;


    //分页查询
    @PostMapping("pagingQuery")
    public Object accountPagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountPagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //编辑查询
    @PostMapping("editQuery")
    public Object accountEditQuery(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountEditQuery(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //新增
    @PostMapping("inAdd")
    public Object accountInsert(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountInsert(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //修改
    @PostMapping("inUpdate")
    public Object accountUpdate(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountUpdate(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //删除
    @PostMapping("inDelete")
    public Object accountDelete(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountDelete(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //批量删除
    @PostMapping("inBatchDelete")
    public Object accountBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iAccountService.accountBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
