/*
package com.ral.manages.controller.app;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.entity.Result;
import com.ral.manages.entity.app.Move;
import com.ral.manages.service.app.IMoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/move/")
public class MoveController {

    private static final Logger LOG = LoggerFactory.getLogger(MoveController.class);
    @Autowired
    private IMoveService iMoveService;


    //分页查询
    @PostMapping("pagingQuery")
    public Object movePagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.movePagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //编辑查询
    @PostMapping("editQuery")
    public Object moveEditQuery(@RequestBody Move move){
        LOG.info("请求参数:" + move);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveEditQuery(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //新增
    @PostMapping("inAdd")
    public Object moveInsert(@RequestBody Move move){
        LOG.info("请求参数:" + move);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveInsert(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //修改
    @PostMapping("inUpdate")
    public Object moveUpdate(@RequestBody Move move){
        LOG.info("请求参数:" + move);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveUpdate(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //删除
    @PostMapping("inDelete")
    public Object moveDelete(@RequestBody Move move){
        LOG.info("请求参数:" + move);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveDelete(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //批量删除
    @PostMapping("inBatchDelete")
    public Object moveBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //效果下拉框
    @GetMapping("effectDownBox")
    public Object effectDownBox(){
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveAddEffect();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //功夫下拉框
    @GetMapping("kongFuDownBox")
    public Object kongFuDownBox(){
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveAddKongFu();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    //查看详情
    @PostMapping("inSee")
    public Object moveSee(@RequestBody Move move){
        LOG.info("请求参数:" + move);
        Result generalResponse = new Result();
        try{
            generalResponse = iMoveService.moveSee(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = Result.error(ResultCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
*/
