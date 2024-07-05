package com.zp;

import com.zp.common.exception.ServiceException;
import com.zp.core.scheduler.JobScheduler;
import com.zp.model.entity.QuartzJob;
import com.zp.service.JobService;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * JobTest
 *
 * @author ZP
 * 
 */
@SpringBootTest
public class JobTest {

    @Resource
    private JobService jobService;

    @Resource
    private JobScheduler jobScheduler;

    private String second = "0/1 * * * * ?";

    private String minute = "0 0/1 * * * ?";

    @Test
    void insertSqlTest() throws ServiceException, ParseException, SQLException {
        List<String> result = jobService.getInsertSql("test_01");
        System.out.println(result.toString());
    }

    @Test
    void quartzJobTest() throws SchedulerException, InterruptedException {
        QuartzJob quartzJob =new QuartzJob();
        quartzJob.setJobGroup("default");
        quartzJob.setJobName("test_01");
        quartzJob.setCronExpression(second);
        jobScheduler.deleteQuartzJob(quartzJob);
        TimeUnit.MINUTES.sleep(10);
        jobScheduler.deleteQuartzJob(quartzJob);
    }

    @Test
    void deleteAll() throws SchedulerException, InterruptedException {
        jobScheduler.deleteAllQuartzJobs();
    }
}
