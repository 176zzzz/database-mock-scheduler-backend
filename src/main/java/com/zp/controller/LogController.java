package com.zp.controller;

import com.zp.core.log.LogGenerator;
import com.zp.model.common.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * LogController
 *
 * @author ZP
 */
@RestController()
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogGenerator logGenerator;

    @GetMapping("fileList")
    public ResponseResult<List<String>> fileList(@RequestParam String code, @RequestParam String level) {
        return ResponseResult.success(logGenerator.getAllFilesByCode(code, level));
    }

    @GetMapping("fileContent")
    public ResponseResult<String> fileContent(@RequestParam String code, @RequestParam String fileName,
                                              @RequestParam String level) {
        return ResponseResult.success(logGenerator.getFileContentByCodeAndFileName(code, fileName, level));
    }

}
