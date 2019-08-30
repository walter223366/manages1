package com.ral.manages.controller.wx;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.Result;
import com.ral.manages.service.wx.IWeCatAuthService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/weChatAuth/")
public class WeChatAuthController {

    private static final Logger log = Logger.getLogger(WeChatAuthController.class);
    @Autowired
    private IWeCatAuthService weCatAuthService;


    @RequestMapping("getCode.do")
    @ResponseBody
    public Object getCode(@RequestParam Map<String,Object> map,HttpServletResponse response) {
        log.info("请求参数：" + map);
        Result result = new Result();
        try {
            String url = weCatAuthService.getCode(map);
            response.sendRedirect(url);
        } catch (BizException ex) {
            log.debug("请求失败：", ex);
            result.setMsg(ex.getMessage());
            result.setCode(ResultCode.FAIL.getCode());
            result.setResult(ResultCode.FAIL.getMsg());
        } catch (Exception e) {
            log.error("请求失败：", e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.ERROR.getCode());
            result.setResult(ResultCode.ERROR.getMsg());
        }
        log.info("返回结果：" + result);
        return result;
    }

    @RequestMapping("getOpenId.do")
    @ResponseBody
    public Object getCode(@RequestParam Map<String,Object> map) {
        log.info("请求参数：" + map);
        Result result = new Result();
        try {
            JSONObject json = weCatAuthService.getOpenid(map);
            result.setRows(json);
        } catch (BizException ex) {
            log.debug("请求失败：", ex);
            result.setMsg(ex.getMessage());
            result.setCode(ResultCode.FAIL.getCode());
            result.setResult(ResultCode.FAIL.getMsg());
        } catch (Exception e) {
            log.error("请求失败：", e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.ERROR.getCode());
            result.setResult(ResultCode.ERROR.getMsg());
        }
        log.info("返回结果：" + result);
        return result;
    }
}
