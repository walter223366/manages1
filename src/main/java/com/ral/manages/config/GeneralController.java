package com.ral.manages.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GeneralController {

    /**
     * 页面跳转
     * @param url
     * @return
     */
    @RequestMapping("{url}")
    public String page(@PathVariable("url") String url) {
        return  url;
    }

    /**
     * 页面跳转(一级目录)
     * @param module
     * @param url
     * @return
     */
    @RequestMapping("{module}/{url}")
    public String page(@PathVariable("module") String module,@PathVariable("url") String url) {
        return module + "/" + url;
    }

    /**
     * 页面跳转（二级目录)
     * @param module
     * @param url
     * @return
     */

    @RequestMapping("{module}/{sub}/{url}")
    public String page(@PathVariable("module") String module,@PathVariable("sub") String sub,@PathVariable("url") String url) {
        return module + "/" + sub + "/" + url;
    }

}
