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
 * @since 2024-05-30 08:56:03
 */
@Getter
@Setter
@TableName("data_source")
public class DataSource {

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("url")
    private String url;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("driver")
    private String driver;


}
