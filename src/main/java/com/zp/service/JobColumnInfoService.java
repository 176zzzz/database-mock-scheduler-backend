package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.model.entity.JobColumnInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:34:38
 */
public interface JobColumnInfoService extends IService<JobColumnInfo> {

    List<JobColumnInfo> getJobColumnListByCode(String code);

}
