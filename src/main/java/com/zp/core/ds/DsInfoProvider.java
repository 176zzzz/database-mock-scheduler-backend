package com.zp.core.ds;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.constant.SysConstant;
import com.zp.common.exception.ServiceException;
import com.zp.model.domain.TableColumnInfo;
import com.zp.model.domain.TableInfo;
import com.zp.model.enums.DbType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * dsInfoProvider
 *
 * @author ZP
 * @since 2024/5/29 15:49
 */
@Repository
@Slf4j
public class DsInfoProvider {

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 获取指定库中表名和表注释
     */
    public List<TableInfo> getTables() throws ServiceException {
        Connection conn = null;
        List<TableInfo> tableInfos = new ArrayList<>();
        try {
            conn = dynamicRoutingDataSource.getDataSource(SysConstant.TARGET_DS_NAME).getConnection();
            ResultSet resultSet = getResultSetByDsType(conn);
            while (resultSet.next()) {
                //表名
                String tableName = resultSet.getString("TABLE_NAME");
                //表注释
                String tableRemark = resultSet.getString("REMARKS");
                TableInfo tableInfo = new TableInfo();
                tableInfo.setName(tableName);
                tableInfo.setComment(tableRemark);
                tableInfos.add(tableInfo);
            }
        } catch (Exception e) {
            log.error("获取数据库表信息失败： {}", e.getMessage());
            throw new ServiceException(ExceptionMsgConstant.TABLE_FAIL);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("数据库关闭失败");
            }
        }
        return tableInfos;
    }

    /**
     * 获取指定表的字段信息（包括字段名称，字段类型，字段长度，备注）
     */
    public List<TableColumnInfo> getTableColumns(String tableName) throws ServiceException {
        Connection conn = null;
        List<TableColumnInfo> tableColumnInfos = new ArrayList<>();
        try {
            conn = dynamicRoutingDataSource.getDataSource(SysConstant.TARGET_DS_NAME).getConnection();
            ResultSet rs = conn.getMetaData().getColumns(null, "%", tableName, "%");
            while (rs.next()) {
                TableColumnInfo tableColumnInfo = new TableColumnInfo();
                //字段名称
                String colName = rs.getString("COLUMN_NAME");
                //字段类型
                String dbType = rs.getString("TYPE_NAME");
                //字段长度
                int columnSize = rs.getInt("COLUMN_SIZE");
                //备注
                String remarks = rs.getString("REMARKS");
                tableColumnInfo.setName(colName);
                tableColumnInfo.setType(dbType);
                tableColumnInfo.setLength(columnSize);
                tableColumnInfo.setComment(remarks);
                tableColumnInfos.add(tableColumnInfo);
            }
        } catch (Exception e) {
            log.error("获取指定数据库表数据列失败: {}", e.getMessage());
            throw new ServiceException(ExceptionMsgConstant.TABLE_COLUMNS_FAIL);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("关闭数据库失败");
            }
        }
        return tableColumnInfos;
    }

    /**
     * 获取指定表指定字段名的信息
     */
    public TableColumnInfo getTableColumn(String tableName, String columnName) throws ServiceException {
        Connection conn = null;
        TableColumnInfo tableColumnInfo = new TableColumnInfo();
        try {
            conn = dynamicRoutingDataSource.getDataSource(SysConstant.TARGET_DS_NAME).getConnection();
            ResultSet rs = conn.getMetaData().getColumns(null, "%", tableName, "%");
            while (rs.next()) {
                //字段名称
                String colName = rs.getString("COLUMN_NAME");
                if (colName.equals(columnName)) {
                    //字段类型
                    String dbType = rs.getString("TYPE_NAME");
                    //字段长度
                    int columnSize = rs.getInt("COLUMN_SIZE");
                    //备注
                    String remarks = rs.getString("REMARKS");
                    tableColumnInfo.setName(colName);
                    tableColumnInfo.setType(dbType);
                    tableColumnInfo.setLength(columnSize);
                    tableColumnInfo.setComment(remarks);
                }
            }
            return tableColumnInfo;
        } catch (Exception e) {
            log.error("获取数据库指定表指定列信息失败: {}", e.getMessage());
            throw new ServiceException(ExceptionMsgConstant.TABLE_COLUMN_FAIL);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("关闭数据库失败");
            }
        }
    }

    public DbType getTargetDbType() throws ServiceException {
        try (Connection connection =
                 dynamicRoutingDataSource.getDataSource(SysConstant.TARGET_DS_NAME).getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            return DbType.getDbType(databaseMetaData.getDatabaseProductName());
        } catch (Exception e) {
            log.error("获取数据源类型失败：{}", e.getMessage());
            throw new ServiceException(ExceptionMsgConstant.DS_TARGET_NULL);
        }

    }

    /**
     * getSchema
     * @param conn Connection
     * @return String
     * 其他数据库不需要这个方法 oracle和db2需要
     */
    private String getSchema(Connection conn) throws Exception {
        String schema;
        schema = conn.getMetaData().getUserName();
        if ((schema == null) || (schema.length() == 0)) {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
        return schema.toUpperCase();
    }

    private ResultSet getResultSetByDsType(Connection connection) throws Exception {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        DbType dbType = DbType.getDbType(databaseMetaData.getDatabaseProductName());
        switch (dbType) {
            case ORACLE:
                return databaseMetaData.getTables(null, getSchema(connection), "%", new String[]{"TABLE"});
            default:
                return databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});
        }
    }
}
