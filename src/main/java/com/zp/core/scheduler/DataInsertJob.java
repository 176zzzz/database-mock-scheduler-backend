package com.zp.core.scheduler;

import org.quartz.JobExecutionContext;

/**
 * Job
 *
 * @author ZP
 * @since 2024/5/17 14:31
 */
public class DataInsertJob implements org.quartz.Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("invoke");
    }
}
