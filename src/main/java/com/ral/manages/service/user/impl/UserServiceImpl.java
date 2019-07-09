package com.ral.manages.service.user.impl;

import com.ral.manages.check.CheckParams;
import com.ral.manages.emun.StateTable;
import com.ral.manages.entity.user.User;
import com.ral.manages.exception.BizException;
import com.ral.manages.mapper.user.IUserMapper;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService , StateTable {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public void loadUserInfo(User user) {
        CheckParams.userCheck(user);
        user.setCancellation(CANCELLATION_ZERO);
        User resultUser = iUserMapper.selectUserInfo(user);
        if(resultUser == null){
            throw new BizException("登录失败，该账号不存在");
        }
        String passworld = resultUser.getPass();
        if(!passworld.equals(resultUser.getPass())){
            throw new BizException("登录失败，账号或密码有误");
        }

    }

    @Override
    public void newUserInfo(User user) {
        int count = iUserMapper.selectUserExist(user);
        if(count > 0){
            throw new BizException("注册失败，该账号已存在");
        }
        user.setCancellation(CANCELLATION_ZERO);
        user.setId(StringUtil.getUUID());
        user.setSource(USER_SOURCE);
        try{
            iUserMapper.insertUserInfo(user);
        }catch (Exception e){
            LOG.debug("注册失败，"+e.getMessage(),e);
            throw new BizException("注册失败，"+e.getMessage());
        }
    }

    @Override
    public void updateUserInfo(User user) {
        CheckParams.userCheck(user);
        User resultUser = iUserMapper.selectUserExistToAccount(user);
        if(resultUser == null){
            throw new BizException("修改失败，该账号不存在");
        }
        int cancellation = resultUser.getCancellation();
        String pass = resultUser.getPass();
        if(!pass.equals(user.getPass())){
            throw new BizException("修改失败，该账号密码错误");
        }
        if(CANCELLATION_ZERO != cancellation){
            throw new BizException("修改失败，该账号已注销");
        }
        int count = iUserMapper.updateUserInfo(user);
       // result.setResult(count == 0?"修改失败":"修改成功");
    }

    @Override
    public void updataUserToCancet(User user) {
        CheckParams.userCheck(user);
        User resultUser = iUserMapper.selectUserExistToAccount(user);
        if(resultUser == null){
            throw new BizException("注销失败，该账号不存在");
        }
        int cancellation = resultUser.getCancellation();
        String pass = resultUser.getPass();
        if(!pass.equals(user.getPass())){
            throw new BizException("注销失败，该账号密码错误");
        }
        if(CANCELLATION_ZERO != cancellation){
            throw new BizException("该账号已被注销过");
        }
        int count = iUserMapper.updateUserInfoToCancellation(user);
      /*  result.setResult(count == 0?"注销失败":"注销成功");
        return result;*/
    }
}
