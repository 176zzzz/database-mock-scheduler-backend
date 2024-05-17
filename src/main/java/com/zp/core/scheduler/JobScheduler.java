package com.zp.core.scheduler;

import com.zp.model.entity.QuartzJob;
import org.quartz.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * JobScheduler,该类负责调度Quartz的任务
 *
 * @author ZP
 * @since 2024/5/17 14:21
 */
@Repository
public class JobScheduler {

    @Resource
    private Scheduler scheduler;

    public void addQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DataInsertJob.class)
            .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
            .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
            .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void deleteQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        JobKey jobKey = new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        scheduler.deleteJob(jobKey);
        scheduler.unscheduleJob(triggerKey);
    }

    public void updateQuartzJob(QuartzJob quartzJob) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        CronTrigger newTrigger = TriggerBuilder.newTrigger()
            .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression()))
            .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    public void changeQuartzJobStatus(QuartzJob quartzJob) throws SchedulerException {

        JobKey jobKey = new JobKey(quartzJob.getJobName(),quartzJob.getJobGroup());
        if (quartzJob.isActivated()) {
            scheduler.resumeJob(jobKey);
        } else {
            scheduler.pauseJob(jobKey);
        }
    }


}
