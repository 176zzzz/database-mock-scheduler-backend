package com.zp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.mapper.JobInfoMapper;
import com.zp.model.entity.JobInfo;
import com.zp.service.JobInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:21:14
 */
@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoMapper, JobInfo> implements JobInfoService {

    @Override
    public JobInfo getByCode(String code,Boolean activated){
        return this.getOne(Wrappers.<JobInfo>lambdaQuery()
            .eq(JobInfo::getCode,code)
            .eq(activated!=null,JobInfo::getActivated,activated));
    }

    @Override
    public List<JobInfo> getByCodeList(List<String> codeList, Boolean activated){
        return this.list(Wrappers.<JobInfo>lambdaQuery()
            .in(JobInfo::getCode,codeList)
            .eq(activated!=null,JobInfo::getActivated,activated));
    }

}
