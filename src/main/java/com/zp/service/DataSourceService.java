package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.common.exception.ServiceException;
import com.zp.model.common.PageResult;
import com.zp.model.entity.DataSource;

/**
 * Application
 *
 * @author ZP
 * @since 2024/5/28 16:09
 */
public interface DataSourceService extends IService<DataSource> {

    /**
     * getList 分页获取数据源列表
     * @param drive String
     * @param pageSize int
     * @param pageNum int
     * @return PageResult<DataSource>
     */
    PageResult<DataSource> getList(String drive, int pageSize, int pageNum);

    /**
     * setDs 设置target数据源
     * @param id Long
     * @return Boolean
     */
    Boolean setDs(Long id) throws ServiceException;

}
