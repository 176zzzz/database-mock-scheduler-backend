package com.zp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * QuartzTestJob
 *
 * @author ZP
 * 
 */
public class QuartzTestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("invoke");
    }
}
