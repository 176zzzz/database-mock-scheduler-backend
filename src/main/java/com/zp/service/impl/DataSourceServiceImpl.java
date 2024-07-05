package com.zp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.constant.SysConstant;
import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsHandle;
import com.zp.mapper.DataSourceMapper;
import com.zp.model.common.PageResult;
import com.zp.model.entity.DataSource;
import com.zp.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-30 08:56:03
 */
@Service
@Slf4j
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Resource
    private DsHandle dsHandle;

    @Override
    public PageResult<DataSource> getList(String drive, int pageSize, int pageNum) {
        Page<DataSource> resultPage = this.page(new Page<>(pageNum, pageSize),
            Wrappers.<DataSource>lambdaQuery().eq(StringUtils.isNotEmpty(drive), DataSource::getDriver, drive));
        return PageResult.<DataSource>builder().data(resultPage.getRecords()).total(resultPage.getTotal()).build();

    }

    @Override
    public Boolean setDs(Long id) throws ServiceException {
        DataSource dataSource = this.getById(id);
        if(dataSource==null){
            log.error(ExceptionMsgConstant.DS_NOT_FOUND);
            throw new ServiceException(ExceptionMsgConstant.DS_NOT_FOUND);
        }
        dsHandle.setDs(SysConstant.TARGET_DS_NAME,dataSource.getUrl(),dataSource.getUsername(),dataSource.getPassword(),dataSource.getDriver());
        return true;
    }

}
