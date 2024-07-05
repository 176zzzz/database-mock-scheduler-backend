package com.zp.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.model.enums.MockDataRuleEnum;
import com.zp.utils.EnumUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
@TableName("job_column_strategy")
public class JobColumnStrategy {

    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("value_type")
    private String valueType;

    @TableField("fixed_value")
    private String fixedValue;

    @TableField("min_value")
    private Integer minValue;

    @TableField("max_value")
    private Integer maxValue;

    @TableField("length")
    private Integer length;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("dic_id")
    private Long dicId;

    @TableField("time_column_name")
    private String timeColumnName;

    @TableField("fun")
    private String fun;

    public boolean validation() throws ServiceException {
        MockDataRuleEnum mockDataRuleEnum = EnumUtil.getEnumByField(MockDataRuleEnum.class, "code", valueType);
        if (mockDataRuleEnum == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_FOUND_STRATEGY);
        }
        switch (mockDataRuleEnum) {
            case FIXED:
                if (StringUtils.isEmpty(fixedValue)) {
                    throw new ServiceException(ExceptionMsgConstant.NOT_FIXED_VALUE);
                }
                return true;
            case INTEGER:
                if (minValue == null || maxValue == null || minValue >= maxValue) {
                    throw new ServiceException(ExceptionMsgConstant.STRATEGY_INTEGER_NULL);
                }
                return true;
            case DECIMAL:
                if (minValue == null || maxValue == null || minValue >= maxValue) {
                    throw new ServiceException(ExceptionMsgConstant.STRATEGY_INTEGER_NULL);
                }
                if (length == null){
                    throw new ServiceException(ExceptionMsgConstant.STRATEGY_LENGTH_NULL);
                }
                return true;
            case FUN:
                if (StringUtils.isEmpty(fun)){
                    throw new ServiceException(ExceptionMsgConstant.FUN_NULL);
                }
                return true;
            case DATE:
                if (startTime == null || endTime ==null || startTime.compareTo(endTime) >=0){
                    throw new ServiceException(ExceptionMsgConstant.STRATEGY_DATE_NULL);
                }
                return true;
            default:
                return true;
        }
    }


}
