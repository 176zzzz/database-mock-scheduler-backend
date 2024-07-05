package com.zp.core.log;

import com.zp.model.enums.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Log
 *
 * @author ZP
 */
@Service
@Slf4j
public class LogGenerator {

    @Value("${log.path}")
    private String path;

    public void insertLog(String code, String content) {
        //1.日志保存路径 形如xxx/code/info/20240527.log
        String logFilePath =
            path + "/" + code + "/" + "/" + LogLevel.INFO.getLevel() + "/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
                ".log";
        //2.保存日志
        insertLogBase(code, content, logFilePath);
    }

    public void insertErrorLog(String code, String content) {
        //1.日志保存路径 形如xxx/code/error/20240527.log
        String logFilePath =
            path + "/" + code + "/" + "/" + LogLevel.ERROR.getLevel() + "/" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
                ".log";
        //2.保存日志
        insertLogBase(code, content, logFilePath);
    }


    public List<String> getAllFilesByCode(String code, String level) {
        List<String> fileNames = new ArrayList<>();
        File directory = new File(path + "/" + code + "/" + level);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        } else {
            log.error("目录不存在或者不是一个目录");
        }
        return fileNames;
    }

    public String getFileContentByCodeAndFileName(String code, String fileName, String level) {
        String filePath = path + "/" + code + "/" + level + "/" + fileName;
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            log.error("读取文件内容失败，code为：{} 文件名为：{} 报错为:{}", code, fileName, e.getMessage());
        }
        return content.toString();
    }


    private void insertLogBase(String code, String content, String path) {
        //1.日志保存路径 形如xxx/code/20240527.log
        String logFilePath = path;

        //2.保存日志
        try {
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(logFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(LocalDateTime.now() + " : " + content);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            log.error("日志保存失败，code为：{} 报错为:{}", code, e.getMessage());
        }
    }


}
