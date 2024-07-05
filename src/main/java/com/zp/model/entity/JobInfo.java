package com.zp.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:21:14
 */
@Data
@TableName("job_info")
public class JobInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private String code;

    @TableField("description")
    private String description;

    @TableField("group_name")
    private String groupName;

    @TableField("cron_expression")
    private String cronExpression;

    @TableField("activated")
    private Boolean activated;

    @TableField("table_name")
    private String tableName;

    @TableField("count")
    private Integer count;


}
