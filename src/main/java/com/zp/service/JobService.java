package com.zp.service;

import com.zp.common.exception.ServiceException;
import com.zp.model.dto.JobInfoListDTO;
import com.zp.model.entity.JobInfo;
import org.quartz.SchedulerException;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * JobService
 *
 * @author ZP
 */
public interface JobService {

    List<String> getInsertSql(String code) throws ServiceException, ParseException, SQLException;

    void runOnce(String code);

    void run(String code) throws ServiceException, SchedulerException;

    void runList(List<String> codeList) throws ServiceException, SchedulerException;

    void delete(String code) throws ServiceException, SchedulerException;

    void deleteList(List<String> codeList) throws SchedulerException, ServiceException;

    void stop(String code) throws ServiceException, SchedulerException;

    void stopList(List<String> codeList) throws SchedulerException, ServiceException;

    void add(JobInfo jobInfo) throws ServiceException, SchedulerException;

    void update(JobInfo jobInfo) throws ServiceException, SchedulerException;

    List<JobInfo> list(JobInfoListDTO jobInfoListDTO);

}
