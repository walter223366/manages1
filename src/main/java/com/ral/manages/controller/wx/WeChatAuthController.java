package com.ral.manages.controller.wx;

import com.ral.manages.comms.emun.ResultCode;
import com.ral.manages.comms.exception.BizException;
import com.ral.manages.entity.Result;
import com.ral.manages.entity.SysConfigurers;
import com.ral.manages.service.wx.IWeCatAuthService;
import com.ral.manages.util.MapUtil;
import com.ral.manages.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@Scope("prototype")
@RequestMapping("/weChatAuth/")
public class WeChatAuthController {

    private static final Logger log = Logger.getLogger(WeChatAuthController.class);
    @Autowired
    private IWeCatAuthService weCatAuthService;
    @Autowired
    private SysConfigurers sys;

    @RequestMapping("getCode.do")
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
            result.setResult(ResultCode.FAIL.getResult());
        } catch (Exception e) {
            log.error("请求失败：", e);
            result.setMsg(e.getMessage());
            result.setCode(ResultCode.ERROR.getCode());
            result.setResult(ResultCode.ERROR.getResult());
        }
        log.info("返回结果：" + result);
        return result;
    }

    @RequestMapping("getOpenId.do")
    public Object getOpenId(@RequestParam Map<String,Object> map) {
        log.info("请求参数：" + map);
        Result result = new Result();
        try {
            JSONObject json = weCatAuthService.getOpenId(map);
            result.setRows(json);
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
        log.info("返回结果：" + result);
        return result;
    }

    //直接获取用户OpenId
    @GetMapping("getOpenId")
    public void getOpenId(@RequestParam Map<String,Object> map,HttpServletResponse response){
        log.info("请求参数：" + map);
        JSONObject result = weCatAuthService.getOpenId(map);
        String openId = result.optString("openid");
        String url = sys.getWeb_url();
        url = url.concat("?openid="+openId);
        log.info("重定向前端页面URL：" + url);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.debug("重定向地址失败："+e.getMessage());
        }
    }

    //获取微信用户信息(用户授权)
    @GetMapping("userInfo")
    public void getAuthUserInfo(@RequestParam Map<String,Object> map,HttpServletResponse response){
        log.info("请求参数：" + map);
        JSONObject result = weCatAuthService.getOpenId(map);
        JSONObject json = weCatAuthService.getUserInfo(map,result);
        String openId = result.optString("openid");
        String url = sys.getWeb_url();
        url = url.concat("?openid="+openId);
        log.info("重定向前端页面URL：" + url);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.debug("重定向地址失败："+e.getMessage());
        }
    }


    //微信网页授权（静默授权）
    @GetMapping("silentRedirect")
    public void getOpenid(@RequestParam Map<String,Object> map,HttpServletResponse response) {
        log.info("请求参数：" + map);
        try {
            String codeUrl = weCatAuthService.getCode(map);
            response.sendRedirect(codeUrl);
        } catch (IOException e) {
            log.error("重定向URL失败："+e.getMessage());
        }
    }

    //微信网页授权（用户授权）
    @GetMapping("/userRedirect")
    public void userRedirect(@RequestParam Map<String,Object> map,HttpServletResponse response){
        log.info("请求参数：" + map);
        map.put("type","userInfo");
        try {
            String codeUrl = weCatAuthService.getCode(map);
            response.sendRedirect(codeUrl);
        } catch (IOException e) {
            log.error("重定向URL失败："+e.getMessage());
        }
    }
}
