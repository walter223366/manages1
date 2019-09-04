package com.ral.manages.controller.app;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.Result;
import com.ral.manages.service.app.ISysAuthService;
import com.ral.manages.util.Base64Util;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/sysAuth/")
public class SysAuthController {

    private static final Logger log = LoggerFactory.getLogger(SysAuthController.class);
    @Autowired
    private ISysAuthService sysAuthService;

    @PostMapping("server")
    public Object sysAuth(@RequestBody Map<String,Object> map) {
        log.info("请求参数：" + map);
        Result result = new Result();
        try {
            Map<String,Object> resultMap = sysAuthService.sysAuth(map);
            JSONObject json = JSONObject.fromObject(resultMap);
            result.setRows(Base64Util.Base64Encode(json.toString()));
            log.info("返回结果：" + result);
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
