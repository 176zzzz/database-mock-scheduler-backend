package com.zp.core.generators;

import com.github.javafaker.Faker;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.model.entity.JobColumnStrategy;
import com.zp.model.enums.ColumnType;
import com.zp.model.enums.MockDataRuleEnum;
import com.zp.service.DicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DataGeneratorTest {

    @Mock
    private DicService mockDicService;
    @Mock
    private Faker mockFaker;

    @InjectMocks
    private DataGenerator dataGeneratorUnderTest;

    @BeforeEach
    void setUp() throws ServiceException {
        dataGeneratorUnderTest.faker = mockFaker;
        Mockito.when(mockDicService.getValueById(1L)).thenReturn("eee");
    }

    @Test
    void testFIXEDValue() throws Exception {
        // Setup
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setValueType(MockDataRuleEnum.FIXED.getCode());
        jobColumnStrategy.setFixedValue("fixedValue");
        // Run the test
        final String result = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.VARCHAR);
        // Verify the results
        assertThat(result).isEqualTo("'fixedValue'");
        jobColumnStrategy.setFixedValue("22");
        final String result1 = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.NUMBER);
        assertThat(result1).isEqualTo("22");
    }

    @Test
    void testDecimal() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setValueType(MockDataRuleEnum.DECIMAL.getCode());
        jobColumnStrategy.setMinValue(22);
        jobColumnStrategy.setMaxValue(33);
        jobColumnStrategy.setLength(3);
        final String result = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.NUMBER);
        double resultD = Double.parseDouble(result);
        System.out.println(result);
        assertThat(resultD).isBetween(22.0, 33.0);
        assertThat(result.length() - result.indexOf(".") - 1).isEqualTo(3);
    }

    @Test
    void testNumber() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setValueType(MockDataRuleEnum.INTEGER.getCode());
        Integer minValue = 223;
        Integer maxValue = 444;
        jobColumnStrategy.setMaxValue(maxValue);
        jobColumnStrategy.setMinValue(minValue);
        String value = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.NUMBER);
        System.out.print(value);
        double resultD = Double.parseDouble(value);
        assertThat(resultD).isBetween(minValue.doubleValue(), maxValue.doubleValue());

    }

    @Test
    void testDate() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setValueType(MockDataRuleEnum.DATE.getCode());
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);
        jobColumnStrategy.setStartTime(startDate);
        jobColumnStrategy.setEndTime(endDate);
        String value = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.DATE);
        System.out.print(value);
        LocalDateTime date = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(ExceptionMsgConstant.DATE_PATTERN));
        assertThat(date).isBetween(startDate, endDate);
    }

    @Test
    void testFun() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setValueType(MockDataRuleEnum.FUN.getCode());
        String fun = "substr('zzz',1,3)";
        jobColumnStrategy.setFun(fun);
        String value = dataGeneratorUnderTest.getValue(jobColumnStrategy, ColumnType.OTHER);
        System.out.print(value);
        assertThat(fun).isEqualTo(value);
    }

    @Test
    void testDic() throws ServiceException {
        final JobColumnStrategy jobColumnStrategy = new JobColumnStrategy();
        jobColumnStrategy.setDicId(1L);
        jobColumnStrategy.setValueType(MockDataRuleEnum.DIC.getCode());
        String value = dataGeneratorUnderTest.getValue(jobColumnStrategy, null);
        System.out.print(value);
        List<String> list = new ArrayList<>();
        list.add("eee");
        assertThat(value).isIn(list);
    }
}
