package com.zp.core.scheduler;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.constant.SysConstant;
import com.zp.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * Job
 * @author ZP
 */
@Slf4j
public class DataInsertJob implements org.quartz.Job {

    @Resource
    private JobService jobService;


    @Override
    @DSTransactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        if (jobDataMap==null||!jobDataMap.containsKey(SysConstant.JOB_KEY_NAME)){
            throw new JobExecutionException(ExceptionMsgConstant.JOB_EXECUTE_FAIL);
        }
        String code = jobDataMap.getString(SysConstant.JOB_KEY_NAME);
        jobService.runOnce(code);
    }




}
