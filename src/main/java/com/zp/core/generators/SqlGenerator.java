package com.zp.core.generators;

import com.zp.common.exception.ServiceException;
import com.zp.model.domain.JobConstruct;

import java.text.ParseException;
import java.util.List;

/**
 * SqlGenerator
 *
 * @author ZP
 * 
 */
public interface SqlGenerator {

    /**
     * 获取插入数据接口
     * @return 插入数据List
     */
    List<String> getInsertSql(JobConstruct jobConstruct) throws ServiceException, ParseException;
}
