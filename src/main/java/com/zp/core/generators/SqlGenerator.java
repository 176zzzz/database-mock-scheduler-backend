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
    * getInsertSql
    * @param jobConstruct JobConstruct
    * @return List<String>
    * @exception ServiceException 服务异常
    * @exception ParseException ParseException
    */
    List<String> getInsertSql(JobConstruct jobConstruct) throws ServiceException, ParseException;
}
