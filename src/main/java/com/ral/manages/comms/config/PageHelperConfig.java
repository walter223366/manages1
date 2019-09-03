package com.ral.manages.comms.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

/**
 *   分页插件配置
 *
 *   @author Double
 *   @since  2019-07-18
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper getPageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helper-dialect","mysql");
        properties.setProperty("reasonable","true");
        properties.setProperty("support-methods-arguments","true");
        properties.setProperty("params","count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}

