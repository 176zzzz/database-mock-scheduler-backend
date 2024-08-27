package com.zp.core.generators.dialect;

import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsInfoProvider;
import com.zp.core.generators.DataGenerator;
import com.zp.core.generators.SqlGenerator;
import com.zp.model.domain.JobConstruct;
import com.zp.model.domain.TableColumnInfo;
import com.zp.model.entity.JobColumnInfo;
import com.zp.model.entity.JobColumnStrategy;
import com.zp.model.enums.ColumnType;
import com.zp.service.JobColumnStrategyService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * MysqlSqlGenerator
 *
 * @author ZP
 * 
 */
@Component
public class MysqlSqlGenerator implements SqlGenerator {

    @Resource
    private JobColumnStrategyService jobColumnStrategyService;

    @Resource
    private DataGenerator dataGenerator;

    @Resource
    private DsInfoProvider dsInfoProvider;

    @Override
    public List<String> getInsertSql(JobConstruct jobConstruct) throws ServiceException, ParseException {
        List<String> insertSqlList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(jobConstruct.getTableName()).append("(");
        jobConstruct.getJobColumnInfoList().forEach(jobColumnInfo -> sql.append(jobColumnInfo.getColumnName()).append(","));
        sql.deleteCharAt(sql.length() - 1).append(")");

        for (Integer i = 0; i < jobConstruct.getCount(); i++) {
            StringBuilder insertSql = new StringBuilder(sql.toString());
            insertSql.append(" values ").append("(");
            for (int i1 = 0; i1 < jobConstruct.getJobColumnInfoList().size(); i1++) {
                JobColumnInfo jobColumnInfo = jobConstruct.getJobColumnInfoList().get(i1);
                TableColumnInfo tableColumn = dsInfoProvider.getTableColumn(jobConstruct.getTableName(),
                    jobColumnInfo.getColumnName());
                ColumnType type = ColumnType.getType(tableColumn.getType());
                Integer strategyId = jobColumnInfo.getStrategyId();
                JobColumnStrategy jobColumnStrategy = jobColumnStrategyService.getById(strategyId);
                if (jobColumnStrategy == null) {
                    throw new ServiceException(ExceptionMsgConstant.NOT_COLUMN_NULL);
                }
                insertSql.append(dataGenerator.getValue(jobColumnStrategy,type)).append(",");

            }
            insertSql.deleteCharAt(insertSql.length() - 1).append(")");
            insertSqlList.add(insertSql.toString());
        }
        return insertSqlList;

    }


}

