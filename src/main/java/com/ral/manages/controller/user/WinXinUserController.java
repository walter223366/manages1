package com.ral.manages.controller.user;

import com.ral.manages.commom.verifi.ProjectConst;
import com.ral.manages.util.HttpSendUtil;
import com.ral.manages.util.ToolsUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *   <p>功能描述：微信授权</p>
 *   <p>创建时间: 2019-08-23 </p>
 *
 *   @author Double
 */
@RestController
public class WinXinUserController {

    private static final Logger LOG = LoggerFactory.getLogger(WinXinUserController.class);

    //微信网页授权（静默授权）静态页面index.html请求返回  跨域
    @GetMapping("/staticRedirect")
    public void staticRedirect(HttpServletRequest request, HttpServletResponse response){
        String method = "index.html";
        String scope = ProjectConst.SNSAPI_BASE;
        getAuthRedirect(request,response,method,scope);
    }

    //微信网页授权（静默授权）
    @GetMapping("/silentRedirect")
    public void silentRedirect(HttpServletRequest request, HttpServletResponse response){
        String method = "getOpenId";
        String scope = ProjectConst.SNSAPI_BASE;
        getAuthRedirect(request,response,method,scope);
    }

    //微信网页授权（用户授权）
    @GetMapping("/userRedirect")
    public void userRedirect(HttpServletRequest request, HttpServletResponse response){
        String method = "userInfo";
        String scope = ProjectConst.SNSAPI_USERINFO;
        getAuthRedirect(request,response,method,scope);
    }


    //直接获取用户OpenId
    @GetMapping("getOpenId")
    public Object getOpenId(HttpServletRequest request,HttpServletResponse response){
        JSONObject result = new JSONObject();
        String code = request.getParameter("code");
        result = getOpenId(request,response,code);
        return result;
    }


    //获取微信用户信息(用户授权)
    @GetMapping("userInfo")
    public Object getAuthUserInfo(HttpServletRequest request,HttpServletResponse response){
        JSONObject result = new JSONObject();
        String code = request.getParameter("code");
        JSONObject json = getOpenId(request,response,code);
        String openId = json.optString("openid");
        String token = json.optString("access_token");
        String url = ProjectConst.GET_USERINFO_URL;
        url = url.replace("ACCESS_TOKEN",token);
        url = url.replace("OPENID",openId);
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("获取OpenId失败："+e.getMessage());
        }
        return result;
    }

    //授权重定向
    private void getAuthRedirect(HttpServletRequest request,HttpServletResponse response,String method,String scope){
        String appId = ProjectConst.APP_ID;
        String codeUrl = ProjectConst.GET_CODE_URL; //获取code地址
        String url = ProjectConst.MANAGESURL;
        url = url.concat(method);
        codeUrl = codeUrl.replace("APPID",appId);
        try {
            url = URLEncoder.encode(url,"utf-8");//地址编码
        } catch (UnsupportedEncodingException e) {
            LOG.error("重定向URL编码失败："+e.getMessage());
        }
        codeUrl = codeUrl.replace("REDIRECT_URI",url);
        codeUrl = codeUrl.replace("SCOPE",scope);
        String state = "state";//重定向后会带上的参数
        codeUrl = codeUrl.replace("STATE",state);
        try {
            response.sendRedirect(codeUrl);
        } catch (IOException e) {
            LOG.error("RESPONSE重定向失败："+e.getMessage());
        }
    }

    //获取OpenId
    private JSONObject getOpenId(HttpServletRequest request, HttpServletResponse response, String code){
        JSONObject result = new JSONObject();
        //封装获取OpenId的微信API
        String tokenUrl = ProjectConst.GET_ACCESSTOKEN_URL;
        String appId = ProjectConst.APP_ID;
        tokenUrl = tokenUrl.replace("APPID",appId);
        String secret = ProjectConst.APP_SECRET;
        tokenUrl = tokenUrl.replace("SECRET",secret);
        tokenUrl = tokenUrl.replace("CODE",code);
        try{
            String str = ToolsUtil.getRequest(tokenUrl);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("获取OpenId失败："+e.getMessage());
        }
        String openId = result.optString("openid");
        LOG.info("获取openId："+openId);
        request.getSession().setAttribute("openId", openId);
        return result;
    }

    //刷新token
    private JSONObject refreshToken(JSONObject json){
        JSONObject result = new JSONObject();
        String openId = json.optString("openid");
        String refreshToken = json.optString("refresh_token");
        String url = ProjectConst.GET_REFRESHTOKEN_URL;
        url = url.replace("OPENID",openId);
        url = url.replace("REFRESH_TOKEN",refreshToken);
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("校验token失败："+e.getMessage());
        }
        return result;
    }

    //校验token是否有效
    private boolean checkTokenValid(JSONObject json){
        JSONObject result = new JSONObject();
        String token = json.optString("access_token");
        String openId = json.optString("openid");
        String url = ProjectConst.GET_CHECKTOKEN_URL;
        url = url.replace("ACCESS_TOKEN",token);
        url = url.replace("OPENID",openId);
        try{
            String str = ToolsUtil.getRequest(url);
            result = JSONObject.fromObject(str);
        }catch (Exception e){
            LOG.debug("校验token失败："+e.getMessage());
        }
        String errcode = result.optString("errcode");
        if("0".equals(errcode)){
            return true;
        }else{
            return false;
        }
    }
}
