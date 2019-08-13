package com.ral.manages.controller.app;

import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.entity.app.Move;
import com.ral.manages.service.app.IMoveService;
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
@RequestMapping("/move")
public class MoveController {

    private static final Logger LOG = LoggerFactory.getLogger(MoveController.class);
    @Autowired
    private IMoveService iMoveService;


    /**
     * 分页查询
     * @param map map
     * @return Object
     */
    @RequestMapping("/pagingQuery")
    public Object movePagingQuery(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.movePagingQuery(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 编辑查询
     * @param move move
     * @return Object
     */
    @RequestMapping("/editQuery")
    public Object moveEditQuery(Move move){
        LOG.info("请求参数:" + move);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveEditQuery(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 新增
     * @param move move
     * @return Object
     */
    @RequestMapping("inAdd")
    public Object moveInsert(Move move){
        LOG.info("请求参数:" + move);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveInsert(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 修改
     * @param move move
     * @return Object
     */
    @RequestMapping("inUpdate")
    public Object moveUpdate(Move move){
        LOG.info("请求参数:" + move);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveUpdate(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 删除
     * @param move move
     * @return Object
     */
    @RequestMapping("inDelete")
    public Object moveDelete(Move move){
        LOG.info("请求参数:" + move);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveDelete(move);
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
    @RequestMapping("inBatchDelete")
    public Object moveBatchDelete(@RequestBody Map<String,Object> map){
        LOG.info("请求参数:" + map);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveBatchDelete(map);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 添加效果下拉框
     * @return Object
     */
    @RequestMapping("effectDownBox")
    public Object effectDownBox(){
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveAddEffect();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 添加功夫下拉框
     * @return Object
     */
    @RequestMapping("kongFuDownBox")
    public Object kongFuDownBox(){
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveAddKongFu();
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }

    /**
     * 查看详情
     * @param move move
     * @return Object
     */
    @RequestMapping("inSee")
    public Object moveSee(Move move){
        LOG.info("请求参数:" + move);
        GeneralResponse generalResponse = new GeneralResponse();
        try{
            generalResponse = iMoveService.moveSee(move);
            LOG.info("返回值:" + generalResponse);
        }catch (Exception e){
            generalResponse = GeneralResponse.error(ResponseStateCode.ERROR.getMsg()+e.getMessage());
            LOG.error(generalResponse.toString());
        }
        return generalResponse;
    }
}
