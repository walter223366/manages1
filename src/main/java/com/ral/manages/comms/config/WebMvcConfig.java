package com.ral.manages.comms.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *   web路径后缀
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{


    /*开启路径后缀匹配*/
   /* @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
       //configurer.setUseRegisteredSuffixPatternMatch(false);
    }*/


    /*URL后缀配置*/
    /*@Bean("doDispatcherUrl")
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet servlet){
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(servlet);
        servletServletRegistrationBean.addUrlMappings("*.do");
        return servletServletRegistrationBean;
    }*/
}
