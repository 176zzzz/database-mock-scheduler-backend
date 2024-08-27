package com.zp.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.core.generators.SqlGenerator;
import com.zp.core.generators.SqlGeneratorFactory;
import com.zp.core.log.LogGenerator;
import com.zp.core.scheduler.JobScheduler;
import com.zp.mapper.TargetMapper;
import com.zp.model.domain.JobConstruct;
import com.zp.model.dto.JobInfoListDTO;
import com.zp.model.entity.JobColumnInfo;
import com.zp.model.entity.JobInfo;
import com.zp.model.entity.QuartzJob;
import com.zp.service.JobColumnInfoService;
import com.zp.service.JobInfoService;
import com.zp.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JobServiceImpl
 *
 * @author ZP
 */
@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Resource
    private JobInfoService jobInfoService;

    @Resource
    private JobColumnInfoService jobColumnInfoService;

    @Resource
    private SqlGeneratorFactory sqlGeneratorFactory;

    @Resource
    private JobScheduler jobScheduler;

    @Resource
    private LogGenerator logGenerator;

    @Resource
    private TargetMapper targetMapper;

    @Override
    public List<String> getInsertSql(String code) throws ServiceException, ParseException, SQLException {
        //1 拼接JobConstruct
        JobInfo jobInfo = jobInfoService.getByCode(code, null);
        if (jobInfo == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_CODE);
        }
        List<JobColumnInfo> jobColumnListByCode = jobColumnInfoService.getJobColumnListByCode(jobInfo.getCode());
        if (CollectionUtils.isEmpty(jobColumnListByCode)) {
            throw new ServiceException(ExceptionMsgConstant.NOT_COLUMN_INFO);
        }
        JobConstruct jobConstruct = new JobConstruct();
        BeanUtils.copyProperties(jobInfo, jobConstruct);
        jobConstruct.setJobColumnInfoList(jobColumnListByCode);

        //2 调用SqlGenerator生成插入语句
        SqlGenerator sqlGenerator = sqlGeneratorFactory.getSqlGenerator();
        return sqlGenerator.getInsertSql(jobConstruct);

    }

    @Override
    @DSTransactional
    public void runOnce(String code){
        try {
            List<String> sqlList = this.getInsertSql(code);
            sqlList.forEach(sql -> {
                String insertLog = code + "开始执行，sql为：" + sql;
                logGenerator.insertLog(code, insertLog);
                targetMapper.insertBySql(sql);
                log.info(insertLog);
                String successLog = code + "执行成功";
                log.info(successLog);
                logGenerator.insertLog(code, successLog);
            });
        } catch (Exception e) {
            String failLog = code + "任务运行失败，报错为:" + e.getMessage();
            logGenerator.insertLog(code, failLog);
            logGenerator.insertErrorLog(code, failLog);
            log.info(failLog);
            throw new RuntimeException("任务运行失败,报错为:" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String code) throws ServiceException, SchedulerException {
        JobInfo jobInfo = jobInfoService.getByCode(code, false);
        if (jobInfo == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_CODE);
        }
        jobScheduler.runQuartzJob(jobInfoToQuartzJob(jobInfo));
        jobInfo.setActivated(true);
        jobInfoService.updateById(jobInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void runList(List<String> codeList) throws ServiceException, SchedulerException {
        List<JobInfo> jobInfoList = getJobInfoList(codeList,false);
        for (JobInfo jobInfo : jobInfoList) {
            run(jobInfo.getCode());
        }
    }

    @Override
    public void add(JobInfo jobInfo) {
        jobInfoService.save(jobInfo);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stop(String code) throws ServiceException, SchedulerException {
        JobInfo jobInfo = jobInfoService.getByCode(code, true);
        if (jobInfo == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_CODE_STOP);
        }
        jobInfo.setActivated(false);
        jobInfoService.updateById(jobInfo);
        QuartzJob quartzJob = getQuartzJobByCode(code);
        jobScheduler.stopQuartzJob(quartzJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopList(List<String> codeList) throws SchedulerException, ServiceException {
        List<JobInfo> jobInfoList = getJobInfoList(codeList,true);
        for (JobInfo jobInfo : jobInfoList) {
            stop(jobInfo.getCode());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String code) throws ServiceException, SchedulerException {
        JobInfo jobInfo = jobInfoService.getByCode(code, false);
        if (jobInfo==null){
            throw new ServiceException(ExceptionMsgConstant.NOT_STOP_INACTIVATED);
        }
        jobInfoService.removeById(code);
        jobScheduler.deleteQuartzJob(getQuartzJobByCode(code));
    }

    @Override
    @Transactional(rollbackFor =Exception.class)
    public void deleteList(List<String> codeList) throws SchedulerException, ServiceException {
        List<JobInfo> jobInfoList = getJobInfoList(codeList,false);
        for (JobInfo jobInfo : jobInfoList) {
            delete(jobInfo.getCode());
        }
    }

    private List<JobInfo> getJobInfoList(List<String> codeList, Boolean activated) throws ServiceException {
        if (CollectionUtils.isEmpty(codeList)){
            codeList = jobInfoService.list().stream().map(JobInfo::getCode).collect(Collectors.toList());
        }
        List<JobInfo> jobInfoList = jobInfoService.getByCodeList(codeList, activated);
        if (CollectionUtils.isEmpty(jobInfoList)){
            throw new ServiceException(ExceptionMsgConstant.NOT_CODE_LIST);
        }
        return jobInfoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JobInfo jobInfo) throws ServiceException, SchedulerException {
        jobInfoService.updateById(jobInfo);
        jobScheduler.updateQuartzJob(jobInfoToQuartzJob(jobInfo));
    }

    @Override
    public List<JobInfo> list(JobInfoListDTO jobInfoListDTO) {
        return jobInfoService.list(Wrappers.<JobInfo>lambdaQuery()
            .like(StringUtils.isNotEmpty(jobInfoListDTO.getCode()), JobInfo::getCode, jobInfoListDTO.getCode())
            .eq(StringUtils.isNotEmpty(jobInfoListDTO.getGroupName()), JobInfo::getGroupName,
                jobInfoListDTO.getGroupName())
            .eq(jobInfoListDTO.getActivated() != null, JobInfo::getActivated, jobInfoListDTO.getActivated())
        );
    }


    private QuartzJob getQuartzJobByCode(String code) throws ServiceException {
        JobInfo jobInfo = jobInfoService.getByCode(code, false);
        if (jobInfo == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_CODE);
        }
        return jobInfoToQuartzJob(jobInfo);
    }

    private QuartzJob jobInfoToQuartzJob(JobInfo jobInfo) {
        QuartzJob quartzJob = new QuartzJob();
        quartzJob.setJobName(jobInfo.getCode());
        quartzJob.setJobGroup(jobInfo.getGroupName());
        quartzJob.setCronExpression(jobInfo.getCronExpression());
        return quartzJob;
    }
}
