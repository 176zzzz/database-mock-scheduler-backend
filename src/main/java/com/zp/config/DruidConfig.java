package com.zp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DruidConfig
 *
 * @author ZP
 * @since 2024/5/17 10:57
 */
@Configuration
public class DruidConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.application.datasource.druid")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
