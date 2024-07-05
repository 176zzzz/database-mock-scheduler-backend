package com.zp;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsHandle;
import com.zp.core.ds.DsInfoProvider;
import com.zp.mapper.TargetMapper;
import com.zp.model.domain.TableColumnInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * DatabaseTest
 *
 * @author ZP
 * @since 2024/5/29 10:55
 */
@SpringBootTest
public class DatabaseTest {

    @Resource
    private DsInfoProvider dsInfoProvider;

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Resource
    private TargetMapper targetMapper;

    @Resource
    private DsHandle dsHandle;

    @Test
    public void testUpdate() throws  ServiceException {
        //数据库连接信息
        String jdbcUrl = "jdbc:mysql://81.70.252.108:3306/test_target?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "admin050399";



        // dsHandle.setDs(SysConstant.TARGET,jdbcUrl,username,password,"com.mysql.cj.jdbc.Driver");
        // dsHandle.resetDefaultDs();
        String tableName = "dic";
        // List<TableInfo> tableNames = dsInfoProvider.getTables();
        // System.out.println(tableNames.toString());
        List<TableColumnInfo> tableColumns = dsInfoProvider.getTableColumns(tableName);
        System.out.println(tableColumns);

        // dsHandle.setDs(SysConstant.TARGET,jdbcUrl,username,password,"com.mysql.cj.jdbc.Driver");
        //
        // String tableName1 = "dic";
        // List<TableInfo> tableNames1 = dsInfoProvider.getTables();
        // System.out.println(tableNames1.toString());
        // List<TableColumnInfo> tableColumns1 = dsInfoProvider.getTableColumns(tableName1);
        // System.out.println(tableColumns1);
    }





}
