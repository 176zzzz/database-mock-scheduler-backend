package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.model.entity.JobInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:21:14
 */
public interface JobInfoService extends IService<JobInfo> {

    /**
     * getByCode 获取任务
     * @param code String
     * @param activated Boolean
     * @return JobInfo
     */
    JobInfo getByCode(String code,Boolean activated);

    /**
     * getByCodeList
 * @param codeList List
 * @param activated Boolean
 * @return List<JobInfo>
     */
    List<JobInfo> getByCodeList(List<String> codeList, Boolean activated);

}
