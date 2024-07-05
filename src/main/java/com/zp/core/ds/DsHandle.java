package com.zp.core.ds;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.ds.ItemDataSource;
import com.baomidou.dynamic.datasource.enums.SeataMode;
import com.zaxxer.hikari.HikariDataSource;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.constant.SysConstant;
import com.zp.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * DataSourceUtils
 *
 * @author ZP
 */
@Slf4j
@Repository
public class DsHandle {

    @Value("${spring.datasource.dynamic.datasource.target.url}")
    private  String url;

    @Value("${spring.datasource.dynamic.datasource.target.username}")
    private  String username;

    @Value("${spring.datasource.dynamic.datasource.target.password}")
    private  String password;

    @Value("${spring.datasource.dynamic.datasource.target.driver-class-name}")
    private  String drive;


    public void setDs(String dsKey, String url, String username, String password, String driverClassName) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        if (StringUtils.isNotBlank(driverClassName)) {
            dataSource.setDriverClassName(driverClassName);
        }
        ItemDataSource itemDataSource = new ItemDataSource(dsKey, dataSource, dataSource, false, false, SeataMode.AT);
        // 更新mp的ds缓存
        SpringUtil.getBean(DynamicRoutingDataSource.class).addDataSource(dsKey, itemDataSource);
        log.info("数据源重置{} {}", dsKey, url);
    }

    public void resetDefaultDs() throws ServiceException {
        if (StringUtils.isEmpty(url)||StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(drive)){
            throw new ServiceException(ExceptionMsgConstant.DS_TARGET_NULL);
        }
        this.setDs(SysConstant.TARGET_DS_NAME,url,username,password,drive);
    }
}
