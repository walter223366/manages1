package com.ral.manages.comms.config;

import com.ral.manages.comms.filter.MyCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *   cors跨域处理
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Configuration
public class MyFilterConfig{

    /*Cors跨域配置*/
    /*@Bean
    public FilterRegistrationBean<MyCorsFilter> corsFilter() {
        FilterRegistrationBean<MyCorsFilter> filterBean = new FilterRegistrationBean<MyCorsFilter>();
        filterBean.setFilter(new MyCorsFilter());
        filterBean.setName("MyCorsFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }*/
}
