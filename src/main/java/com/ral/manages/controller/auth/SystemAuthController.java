package com.ral.manages.controller.auth;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.Result;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/server/")
public class SystemAuthController {

    private static final Logger log = Logger.getLogger(SystemAuthController.class);


    @RequestMapping("auth")
    @ResponseBody
    public Object server(@RequestParam Map<String,Object> map) {
        log.info("请求参数："+map);
        Result result = new Result();
        try {
            //JSONObject json = weCatAuthService.getOpenid(map);
            //result.setRows(json);
        } catch (BizException ex) {
            log.debug("请求失败：",ex);
            result.setMsg(ex.getMessage());
            result.setCode(ResultCode.FAIL.getCode());
            result.setResult(ResultCode.FAIL.getMsg());
        } catch (Exception e) {
            log.error("请求失败：",e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.ERROR.getCode());
            result.setResult(ResultCode.ERROR.getMsg());
        }
        log.info("返回结果："+result);
        return result;
    }
}
