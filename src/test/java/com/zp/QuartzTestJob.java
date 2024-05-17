package com.zp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * QuartzTestJob
 *
 * @author ZP
 * @since 2024/5/17 11:11
 */
public class QuartzTestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("invoke");
    }
}
