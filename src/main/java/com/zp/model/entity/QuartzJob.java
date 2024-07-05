package com.zp.model.entity;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * QuartzJob
 *
 * @author ZP
 * 
 */
@Data
public class QuartzJob {

    @NotNull()
    private String jobName;

    @NotNull()
    private String jobGroup;

    @NotNull()
    private String cronExpression;

}
