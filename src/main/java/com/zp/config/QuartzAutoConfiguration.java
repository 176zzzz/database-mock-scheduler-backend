package com.zp.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.zp.common.constant.SysConstant;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author ZP
 */
@Configuration
public class QuartzAutoConfiguration {

    @Primary
    @Bean
    public DataSource dataSource(List<DynamicDataSourceProvider> providers, DynamicDataSourceProperties properties) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource(providers);
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setP6spy(properties.getP6spy());
        dataSource.setSeata(properties.getSeata());
        return dataSource;
    }

   @QuartzDataSource
   @Bean
   public DataSource quartzDataSource(DataSource dataSource) {
       DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
       return ds.getDataSource(SysConstant.QUARTZ_DS_NAME);
   }

}