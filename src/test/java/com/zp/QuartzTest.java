package com.zp;

import com.zp.common.exception.ServiceException;
import com.zp.model.entity.QuartzJob;
import com.zp.core.scheduler.JobScheduler;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * QuartzTestJob
 *
 * @author ZP
 * 
 */
@SpringBootTest
public class QuartzTest {

    @Resource
    private JobScheduler jobScheduler;

    private String second = "0/1 * * * * ?";

    @Test
    void jobTest() throws SchedulerException, InterruptedException {
        // 1、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)1
        JobDetail jobDetail = JobBuilder.newJob(QuartzTestJob.class)
            .withIdentity("job1", "group1").build();
        // 2、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
            .startNow()//立即生效
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)//每隔1s执行一次
                .repeatForever()).build();//一直执行

        //3、创建调度器Scheduler并执行
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start ! ------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown ! ------------");
    }

    @Test
    void jobSchedulerTest() throws SchedulerException, InterruptedException, ServiceException {
        QuartzJob quartzJob =new QuartzJob();
        quartzJob.setJobGroup("default");
        quartzJob.setJobName("test_01");
        quartzJob.setCronExpression(second);
        jobScheduler.deleteQuartzJob(quartzJob);
        //启动
        System.out.println("启动");
        jobScheduler.runQuartzJob(quartzJob);
        TimeUnit.SECONDS.sleep(10);

        //暂停
        System.out.println("暂停");
        jobScheduler.stopQuartzJob(quartzJob);
        TimeUnit.SECONDS.sleep(10);

        //启动
        System.out.println("启动");
        jobScheduler.runQuartzJob(quartzJob);
        TimeUnit.SECONDS.sleep(10);

        System.out.println("更新");
        quartzJob.setCronExpression("0/2 * * * * ?");
        jobScheduler.updateQuartzJob(quartzJob);
        TimeUnit.SECONDS.sleep(10);

        jobScheduler.deleteQuartzJob(quartzJob);
    }

}
