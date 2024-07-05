package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.common.exception.ServiceException;
import com.zp.model.common.PageResult;
import com.zp.model.entity.DataSource;


public interface DataSourceService extends IService<DataSource> {

    PageResult<DataSource> getList(String drive, int pageSize, int pageNum);

    Boolean setDs(Long id) throws ServiceException;

}
