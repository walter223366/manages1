package com.ral.manages.controller.wx;

import com.ral.manages.comms.exception.BizException;
import com.ral.manages.service.wx.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Scope("prototype")

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private IAuthService authService;

    @RequestMapping("permissionIn")
    public String permissionIn(@RequestParam Map<String,Object> map) {
        String rurl = "";
        try {
            rurl = (String) authService.permissionIn(map);
        } catch (BizException be) {
            rurl = rurl + "#" + be.getMessage();
        } catch (Exception e) {
            rurl = rurl + "#系统繁忙";
        }
        return "redirect:" + rurl;
    }

    @RequestMapping("permissionOut")
    @ResponseBody
    public String permissionOut(@RequestParam Map<String,Object> map) {
        String rurl = "";
        try {
            rurl = (String) authService.permissionOut(map);
        } catch (BizException be) {
            rurl = rurl + "#" + be.getMessage();
        } catch (Exception e) {
            rurl = rurl + "#系统繁忙";
        }
        return "redirect:" + rurl;
    }

}
