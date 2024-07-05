package com.zp;

import com.zp.common.exception.ServiceException;
import com.zp.core.log.LogGenerator;
import com.zp.mapper.TargetMapper;
import com.zp.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * TransactionalTest
 *
 * @author ZP
 * @since 2024/5/28 9:20
 */
@SpringBootTest
public class TransactionalTest {

    @Resource
    private JobService jobService;

    @Resource
    private LogGenerator logGenerator;

    @Resource
    private TargetMapper targetMapper;

    @Test
    @Transactional
    void test() throws ServiceException, ParseException {
        String code = "test_01";
        try {
            List<String> sqlList = jobService.getInsertSql(code);
            sqlList.forEach(sql -> {
                String insertLog = code + "开始执行，sql为：" + sql;
                logGenerator.insertLog(code, insertLog);
                targetMapper.insertBySql(sql);
                String successLog = code + "执行成功";
                logGenerator.insertLog(code, successLog);
            });
        } catch (Exception e) {
            String failLog = code+"任务运行失败，报错为:"+e.getMessage();
            logGenerator.insertLog(code,failLog);
            logGenerator.insertErrorLog(code,failLog);
        }
    }


}
