/*
package com.ral.manages.commom.config;

import com.ral.manages.commom.filter.MyCorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFilterConfig{

    //Cors跨域配置
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new MyCorsFilter());
        filterBean.setName("MyCorsFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

}
*/
