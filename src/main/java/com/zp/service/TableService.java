package com.zp.service;

import com.zp.common.exception.ServiceException;
import com.zp.model.domain.TableInfo;

import java.util.List;

/**
 * TableService
 *
 * @author ZP
 * @since 2024/5/30 10:32
 */
public interface TableService {

    /**
     * tableList
     * @return List<TableInfo>
     */
    List<TableInfo> tableList() throws ServiceException;
}
