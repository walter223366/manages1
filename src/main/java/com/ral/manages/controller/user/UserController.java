package com.ral.manages.controller.user;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.Result;
import com.ral.manages.service.user.IUserService;
import com.ral.manages.util.Base64Util;
import com.ral.manages.util.MapUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/user/")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @PostMapping("landing")
    public Object sysAuth(@RequestBody Map<String,Object> map) {
        log.info("请求参数：" + map);
        Result result = new Result();
        try {
            userService.landingInfo(map);
            JSONObject json = new JSONObject();
            String url = "/manages/system/main?username="+ MapUtil.getString(map,"username");
            json.put("url",url);
            result.setRows(Base64Util.Base64Encode(json.toString()));
        } catch (BizException ex) {
            log.debug("请求失败：", ex);
            result.setMsg(ex.getMessage());
            result.setCode(ResultCode.FAIL.getCode());
            result.setResult(ResultCode.FAIL.getResult());
        } catch (Exception e) {
            log.error("请求失败：", e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.ERROR.getCode());
            result.setResult(ResultCode.ERROR.getResult());
        }
        return result;
    }
}
