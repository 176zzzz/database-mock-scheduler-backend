package com.zp.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsInfoProvider;
import com.zp.model.domain.TableInfo;
import com.zp.service.TableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TableServiceImpl
 *
 * @author ZP
 * @since 2024/5/30 10:33
 */
@Service
public class TableServiceImpl implements TableService {

    @Resource
    private DsInfoProvider dsInfoProvider;

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Override
    public List<TableInfo> tableList() throws ServiceException {
        return dsInfoProvider.getTables();
    }
}
