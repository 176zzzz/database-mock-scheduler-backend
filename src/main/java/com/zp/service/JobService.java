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

    /**
     * getInsertSql 获取插入sql
     * @param code String
     * @return List<String>
     */
    List<String> getInsertSql(String code) throws ServiceException, ParseException, SQLException;

    /**
     * runOnce
     * @param code String
     */
    void runOnce(String code);

    /**
     * run
     * @param code String
     */
    void run(String code) throws ServiceException, SchedulerException;

    /**
     * runList
     * @param codeList List
     */
    void runList(List<String> codeList) throws ServiceException, SchedulerException;

    /**
     * delete
     * @param code String
     */
    void delete(String code) throws ServiceException, SchedulerException;

    /**
     * deleteList
     * @param codeList List
     */
    void deleteList(List<String> codeList) throws SchedulerException, ServiceException;

    /**
     * stop
     * @param code String
     */
    void stop(String code) throws ServiceException, SchedulerException;

    /**
     * stopList
     * @param codeList List
     */
    void stopList(List<String> codeList) throws SchedulerException, ServiceException;

    /**
     * add
     * @param jobInfo JobInfo
     */
    void add(JobInfo jobInfo) throws ServiceException, SchedulerException;

    /**
     * update
     * @param jobInfo JobInfo
     */
    void update(JobInfo jobInfo) throws ServiceException, SchedulerException;

    /**
     * list
 * @param jobInfoListDTO JobInfoListDTO
 * @return List<JobInfo>
     */
    List<JobInfo> list(JobInfoListDTO jobInfoListDTO);

}
