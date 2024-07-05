package com.zp.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-21 10:34:38
 */
@Getter
@Setter
@TableName("job_column_info")
public class JobColumnInfo {

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("job_code")
    private String jobCode;

    @TableField("strategy_id")
    private Integer strategyId;

    @TableField("column_name")
    private String columnName;


}
