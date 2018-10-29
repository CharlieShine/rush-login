package com.rush.house.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by 17512 on 2018/10/28.
 */
//@Configuration
//@PropertySource("classpath:application.properties")
public class DatasourceConfig {
//    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
//    @Value("${spring.datasource.url}")
    private String url;
//    @Value("${spring.datasource.username}")
    private String username;
//    @Value("${spring.datasource.password}")
    private String password;
//    @Value("${spring.datasource.type}")
    private String type;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Bean
    public DruidDataSource dataSource () {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(this.url);
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDbType(this.type);
        return dataSource;
    }
}
