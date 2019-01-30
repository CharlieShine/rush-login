package com.rush.config;

import com.rush.filter.CrossOriginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * 暂时没有用了, 因为filter都加在shiro配置里面了
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean getDemoFilter(){
        CrossOriginFilter filter = new CrossOriginFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
