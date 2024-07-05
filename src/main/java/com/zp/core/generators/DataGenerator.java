package com.zp.core.generators;

import com.github.javafaker.Faker;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.model.entity.JobColumnStrategy;
import com.zp.model.enums.ColumnType;
import com.zp.model.enums.MockDataRuleEnum;
import com.zp.service.DicService;
import com.zp.utils.EnumUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * dataGenerator
 *
 * @author ZP
 */
@Service
public class DataGenerator {

    @Resource
    private DicService dicService;

    Faker faker = new Faker(Locale.CHINA);

    public String getValue(JobColumnStrategy jobColumnStrategy, ColumnType columnType) throws ServiceException {
        jobColumnStrategy.validation();
        String valueType = jobColumnStrategy.getValueType();
        MockDataRuleEnum mockDataRuleEnum = EnumUtil.getEnumByField(MockDataRuleEnum.class, "code", valueType);
        if (mockDataRuleEnum == null) {
            throw new ServiceException(ExceptionMsgConstant.NOT_FOUND_STRATEGY);
        }
        return getValueByType(mockDataRuleEnum, jobColumnStrategy, columnType);
    }

    private String getValueByType(MockDataRuleEnum mockDataRuleEnum, JobColumnStrategy jobColumnStrategy,
                                  ColumnType columnType) throws ServiceException {

        switch (mockDataRuleEnum) {
            case FIXED:
                return getValueByType(jobColumnStrategy.getFixedValue(), columnType);
            case INTEGER:
                return String.valueOf((int) generateRandomNumber(jobColumnStrategy.getMinValue(),
                    jobColumnStrategy.getMaxValue(), 0));
            case DECIMAL:
                double resultD = generateRandomNumber(jobColumnStrategy.getMinValue(),
                    jobColumnStrategy.getMaxValue(), jobColumnStrategy.getLength());
                BigDecimal bigDecimal = BigDecimal.valueOf(resultD).setScale(jobColumnStrategy.getLength(),
                    RoundingMode.HALF_UP);
                return bigDecimal.toString();
            case DATE:
                return generateRandomTime(jobColumnStrategy.getStartTime(),
                    jobColumnStrategy.getEndTime(), ExceptionMsgConstant.DATE_PATTERN);
            case FUN:
                return jobColumnStrategy.getFun();
            case DIC:
                return getValueByType(dicService.getValueById(jobColumnStrategy.getDicId()), columnType);
            case NAME:
                return getValueByType(faker.name().name(), columnType);
            case ADDRESS:
                return getValueByType(faker.address().city(), columnType);
            case TELEPHONE:
                return getValueByType(faker.phoneNumber().cellPhone(), columnType);
            default:
                return null;
        }
    }

    private String getValueByType(String value, ColumnType columnType) {
        switch (columnType) {
            case VARCHAR:
            case CHAR:
            case DATE:
            case TIMESTAMP:
            case TIME:
            case TEXT:
                return convertToString(value);
            default:
                return value;
        }
    }

    private String convertToString(String source) {
        return "'" + source + "'";
    }

    private double generateRandomNumber(double min, double max, int decimalPlaces) {
        if (min >= max || decimalPlaces < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // 计算随机数的范围
        double range = max - min;

        // 生成随机数
        Random random = new Random();
        double randomValue = min + (range * random.nextDouble());

        // 保留指定小数位数
        double factor = Math.pow(10, decimalPlaces);
        randomValue = Math.round(randomValue * factor) / factor;

        return randomValue;
    }

    private String generateRandomTime(LocalDateTime startTime, LocalDateTime endTime, String timeFormat) {

        // 将开始时间和结束时间转换为毫秒数
        long startMillis = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 生成在开始和结束时间之间的随机毫秒数
        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis + 1);

        // 将随机毫秒数转换为Date对象
        LocalDateTime randomDateTime =
            Instant.ofEpochMilli(randomMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 将随机时间格式化为指定格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        return convertToString(randomDateTime.format(formatter));
    }

}
