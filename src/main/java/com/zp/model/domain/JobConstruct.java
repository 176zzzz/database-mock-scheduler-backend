package com.zp.model.domain;

import com.zp.model.entity.JobColumnInfo;
import lombok.Data;

import java.util.List;

/**
 * JobDetail
 *
 * @author ZP
 * 
 */
@Data
public class JobConstruct {

    private Long id;

    private String code;

    private String description;

    private String groupName;

    private String cronExpression;

    private Integer activated;

    private String tableName;

    private Integer count;

    private List<JobColumnInfo> jobColumnInfoList;
}
