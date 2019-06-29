package com.ral.manages.mapper.account;

import com.ral.manages.entity.account.AccountEntity;
import java.util.List;
import java.util.Map;

/**
 * 用户账号mapper
 * @author double
 */
public interface IAccountMapper {

    //用户账号登录
    AccountEntity selectAccountInfo(AccountEntity accountEntity);

    //用户账号查询
    List<AccountEntity> selectAccountInfo(Map<String, Object> map);

    //用户账号新增
    int insertAccountInfo(AccountEntity accountEntity);

    //用户账号修改
    int updateAccountInfo(AccountEntity accountEntity);

    //用户账号删除
    int deleteAccountInfo(AccountEntity accountEntity);
}
