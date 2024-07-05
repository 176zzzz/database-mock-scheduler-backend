package com.zp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.mapper.JobColumnInfoMapper;
import com.zp.model.entity.JobColumnInfo;
import com.zp.service.JobColumnInfoService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:34:38
 */
@Service
public class JobColumnInfoServiceImpl extends ServiceImpl<JobColumnInfoMapper, JobColumnInfo> implements JobColumnInfoService {

    @Override
    public List<JobColumnInfo> getJobColumnListByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<JobColumnInfo>lambdaQuery().eq(JobColumnInfo::getJobCode, code));
    }

}
