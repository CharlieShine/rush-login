package com.rush.house.config;

import com.rush.house.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17512 on 2018/10/29.
 */
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean getDemoFilter(){
        LoginFilter filter = new LoginFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/user/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
