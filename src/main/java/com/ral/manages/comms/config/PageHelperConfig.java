package com.ral.manages.comms.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

/**
 *   <p>功能描述：分页插件配置类</p>
 *   <p>创建时间：2019-07-18 </p>
 *
 *   @author Double
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

