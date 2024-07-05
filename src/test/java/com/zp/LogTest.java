package com.zp;

import com.zp.core.log.LogGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * LogTest
 *
 * @author ZP
 * 
 */
@SpringBootTest
public class LogTest {

    @Resource
    private LogGenerator logGenerator;

    @Test
    void insertLongTest(){
        logGenerator.insertLog("222","dddd");
    }

    @Test
    void getFileList(){
        List<String> allFilesByCode = logGenerator.getAllFilesByCode("222","info");
        System.out.println(allFilesByCode);
    }

    @Test
    void readFile(){
        String fileContentByCodeAndFileName = logGenerator.getFileContentByCodeAndFileName("222", "20240526.log","info");
        System.out.println(fileContentByCodeAndFileName);
    }
}
