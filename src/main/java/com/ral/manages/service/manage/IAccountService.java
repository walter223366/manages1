package com.ral.manages.service.manage;

import com.ral.manages.exception.GeneralResponse;
import java.util.Map;

public interface IAccountService {

    //查询
    GeneralResponse selectAccountToPage(Map<String,Object> map);

}
