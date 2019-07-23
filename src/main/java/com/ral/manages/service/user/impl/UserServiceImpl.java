package com.ral.manages.service.user.impl;

import com.ral.manages.entity.user.User;
import com.ral.manages.mapper.user.IUserMapper;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public void loadUserInfo(User user) {
        user.setCancellation(0);
        User resultUser = iUserMapper.selectUserInfo(user);
    }

    @Override
    public void newUserInfo(User user) {
        int count = iUserMapper.selectUserExist(user);
        user.setCancellation(0);
        user.setId(StringUtil.getUUID());
        //int source = (int) StateTable.USER_SOURCE.getCode();
        iUserMapper.insertUserInfo(user);

    }

    @Override
    public void updateUserInfo(User user) {
        User resultUser = iUserMapper.selectUserExistToAccount(user);
        int cancellation = resultUser.getCancellation();
        int count = iUserMapper.updateUserInfo(user);
    }

    @Override
    public void updataUserToCancet(User user) {
        User resultUser = iUserMapper.selectUserExistToAccount(user);
        int cancellation = resultUser.getCancellation();
        int count = iUserMapper.updateUserInfoToCancellation(user);
    }
}
