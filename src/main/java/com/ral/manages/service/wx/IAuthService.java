package com.ral.manages.service.wx;

import java.util.Map;

public interface IAuthService {

    Object permissionIn(Map<String,Object> map);

    Object permissionOut(Map<String,Object> map);
}
