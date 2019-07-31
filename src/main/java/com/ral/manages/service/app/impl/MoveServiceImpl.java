package com.ral.manages.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ral.manages.commom.emun.ResponseStateCode;
import com.ral.manages.commom.page.PageBean;
import com.ral.manages.commom.response.GeneralResponse;
import com.ral.manages.commom.verification.VerificationParams;
import com.ral.manages.entity.app.Move;
import com.ral.manages.mapper.app.IKongFuMapper;
import com.ral.manages.mapper.app.IMoveMapper;
import com.ral.manages.service.app.IMoveService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class MoveServiceImpl implements IMoveService {

    private static final Logger LOG = LoggerFactory.getLogger(MoveServiceImpl.class);
    @Autowired
    private IMoveMapper iMoveMapper;
    @Autowired
    private IKongFuMapper iKongFuMapper;

    /**
     * 分页查询
     * @param map map
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse movePagingQuery(Map<String,Object> map) {
        Page<Map<String,Object>> page = PageHelper.startPage(PageBean.pageNum(map),PageBean.pageSize(map));
        List<Map<String,Object>> zhaoShiList = iMoveMapper.selectMovePagingQuery(map);
        return GeneralResponse.success(ResponseStateCode.SUCCESS.getMsg(),PageBean.resultPage(page.getTotal(),zhaoShiList));
    }

    /**
     * 新增
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveAdd(Move move) {
        String msg = VerificationParams.verificationZhaoShi(move);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iMoveMapper.selectMoveToName(move);
        if(count > 0){
            return GeneralResponse.fail("新增失败，该招式名称已存在");
        }
        move.setZhaoshi_id(StringUtil.getUUID());
        try{
            iMoveMapper.insertMove(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 修改
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveUpdate(Move move) {
        String msg = VerificationParams.verificationZhaoShi(move);
        if(!StringUtil.isNull(msg)){
            return GeneralResponse.fail(msg);
        }
        int count = iMoveMapper.selectMoveToExist(move);
        if(count <= 0){
            return GeneralResponse.fail("修改失败，该招式名称不存在");
        }
        try{
            iMoveMapper.updateMove(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 删除
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveDelete(Move move) {
        int count = iMoveMapper.selectMoveToExist(move);
        if(count <= 0){
            return GeneralResponse.fail("删除失败，该招式名称不存在");
        }
        try{
            iMoveMapper.deleteMove(move);
            return GeneralResponse.successNotdatas(ResponseStateCode.SUCCESS.getMsg());
        }catch (Exception e){
            LOG.debug(ResponseStateCode.FAIL.getMsg()+e.getMessage(),e);
            return GeneralResponse.fail(ResponseStateCode.FAIL.getMsg()+e.getMessage());
        }
    }

    /**
     * 详情
     * @param move move
     * @return GeneralResponse
     */
    @Override
    public GeneralResponse moveDetails(Move move) {
        if(StringUtil.isNull(move.getName())){
            return
        }
        return null;
    }
}
