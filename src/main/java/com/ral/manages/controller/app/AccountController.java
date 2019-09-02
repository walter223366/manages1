/*
package com.ral.manages.controller.app;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.entity.app.Account;
import com.ral.manages.entity.Result;
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
        Result result = new Result();
        try{
            iAccountService.accountPagingQuery(map);

        }catch (Exception e){
            result = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());

        }
        return result;
    }

    //编辑查询
    @PostMapping("editQuery")
    public Object accountEditQuery(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountEditQuery(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //新增
    @PostMapping("inAdd")
    public Object accountInsert(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountInsert(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //修改
    @PostMapping("inUpdate")
    public Object accountUpdate(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountUpdate(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //删除
    @PostMapping("inDelete")
    public Object accountDelete(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountDelete(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //批量删除
    @PostMapping("inBatchDelete")
    public Object accountBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //用户登录、注册
    @PostMapping("accountSignUp")
    public Object accountSignUp(@RequestBody Account account){
        LOG.info("请求参数:" + account);
        Result generalResponse = new Result();
        try{
            generalResponse = iAccountService.accountSignUp(account);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
*/
