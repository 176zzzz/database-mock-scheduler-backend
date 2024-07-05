package com.zp.core.generators;

import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsInfoProvider;
import com.zp.core.generators.dialect.MysqlSqlGenerator;
import com.zp.model.enums.DbType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SqlGeneratorFactory
 *
 * @author ZP
 */
@Service
public class SqlGeneratorFactory {

    @Resource
    private MysqlSqlGenerator mysqlSqlGenerator;

    @Resource
    private DsInfoProvider dsInfoProvider;

    public SqlGenerator getSqlGenerator() throws ServiceException {

        DbType targetDbType = dsInfoProvider.getTargetDbType();
        switch (targetDbType) {
            case MYSQL:
                return mysqlSqlGenerator;
            default:
                return mysqlSqlGenerator;
        }
    }
}
