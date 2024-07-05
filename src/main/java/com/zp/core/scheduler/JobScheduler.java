package com.zp.core.scheduler;

import com.zp.common.constant.SysConstant;
import com.zp.model.entity.QuartzJob;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * JobScheduler,该类负责调度Quartz的任务
 * @author ZP
 */
@Service
@Validated
public class JobScheduler {

    @Resource
    private Scheduler scheduler;

    public void deleteQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        scheduler.deleteJob(jobKey);
        scheduler.unscheduleJob(triggerKey);
    }

    public void deleteAllQuartzJobs() throws SchedulerException {
        List<JobExecutionContext> jobExecutions = scheduler.getCurrentlyExecutingJobs();

        for (JobExecutionContext jobExecution : jobExecutions) {
            JobKey jobKey = jobExecution.getJobDetail().getKey();
            TriggerKey triggerKey = jobExecution.getTrigger().getKey();
            scheduler.deleteJob(jobKey);
            scheduler.unscheduleJob(triggerKey);
        }
    }


    public void updateQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        CronTrigger newTrigger = TriggerBuilder.newTrigger()
            .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
            .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    public void runQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        if (checkJob(quartzJob)) {
            scheduler.resumeJob(jobKey);
        } else {
            JobDetail jobDetail = JobBuilder.newJob(DataInsertJob.class)
                .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                .usingJobData(SysConstant.JOB_KEY_NAME, quartzJob.getJobName())
                .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
                .build();
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public void stopQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        checkJob(quartzJob);
        scheduler.pauseJob(jobKey);
    }

    private boolean checkJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        return scheduler.checkExists(jobKey);
    }


}
