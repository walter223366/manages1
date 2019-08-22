package com.ral.manages.controller.jump;

import com.ral.manages.commom.exception.AesException;
import com.ral.manages.util.SHA1Util;
import com.ral.manages.util.SetUtil;
import com.ral.manages.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;


@Controller
public class VerTokenController {
    private static final Logger LOG = LoggerFactory.getLogger(VerTokenController.class);

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public Object verToken(HttpServletRequest request){
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(StringUtil.isNull(signature) || StringUtil.isNull(timestamp) || StringUtil.isNull(nonce) || StringUtil.isNull(echostr)){
            return "start task.......";
        }else {
            System.out.println("signature：" + signature + "，echostr：" + echostr);
            String token = "aiurorigin";
            String result = "";
            try {
                result = SHA1Util.getSHA1(token, timestamp, nonce);
            } catch (AesException e) {
                LOG.info(e.getMessage(), e);
            }
            System.out.println("result:" + result);
            if (signature.equals(result)) {
                return echostr;
            } else {
                return false;
            }
        }
    }
}
