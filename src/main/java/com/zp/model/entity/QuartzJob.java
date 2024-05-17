package com.zp.model.entity;

import lombok.Data;

/**
 * QuartzJob
 *
 * @author ZP
 * @since 2024/5/17 14:30
 */
@Data
public class QuartzJob {

    private String jobName;

    private String jobGroup;

    private String cronExpression;

    private boolean activated;

}
