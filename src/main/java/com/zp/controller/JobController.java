package com.zp.controller;

import com.zp.common.exception.ServiceException;
import com.zp.model.common.ResponseResult;
import com.zp.model.dto.JobInfoListDTO;
import com.zp.model.entity.JobInfo;
import com.zp.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * JobController
 *
 * @author ZP
 */
@RestController()
@RequestMapping("/job")
public class JobController {

    @Resource
    private JobService jobService;

    @GetMapping(value = "/getInsertSql")
    public ResponseResult<List<String>> getInsertSql(String code) throws ServiceException, ParseException,
        SQLException {
        return ResponseResult.success(jobService.getInsertSql(code));
    }

    @GetMapping(value = "/runOnce")
    public ResponseResult<String> runOnce(String code) {
        jobService.runOnce(code);
        return ResponseResult.success();
    }

    @GetMapping(value = "/run")
    public ResponseResult<String> run(@RequestParam String code) throws ServiceException, SchedulerException {
        jobService.run(code);
        return ResponseResult.success();
    }

    @GetMapping(value = "/runList")
    public ResponseResult<String> runList(@RequestParam List<String> codeList) throws SchedulerException,
        ServiceException {
        jobService.runList(codeList);
        return ResponseResult.success();
    }

    @GetMapping(value = "/stop")
    public ResponseResult<String> stop(@RequestParam String code) throws ServiceException, SchedulerException {
        jobService.stop(code);
        return ResponseResult.success();
    }

    @GetMapping(value = "/stopList")
    public ResponseResult<String> stopList(@RequestParam List<String> codeList) throws SchedulerException,
        ServiceException {
        jobService.stopList(codeList);
        return ResponseResult.success();
    }

    @PostMapping(value = "/add")
    public ResponseResult<String> save(@RequestBody JobInfo jobInfo) throws ServiceException, SchedulerException {
        jobService.add(jobInfo);
        return ResponseResult.success();
    }

    @GetMapping(value = "/delete")
    public ResponseResult<String> delete(@RequestParam String code) throws ServiceException, SchedulerException {
        jobService.delete(code);
        return ResponseResult.success();
    }

    @GetMapping(value = "/deleteList")
    public ResponseResult<String> deleteList(@RequestParam List<String> codeList) throws SchedulerException, ServiceException {
        jobService.deleteList(codeList);
        return ResponseResult.success();
    }

    @PostMapping(value = "/update")
    public ResponseResult<String> update(@RequestBody JobInfo jobInfo) throws ServiceException, SchedulerException {
        jobService.update(jobInfo);
        return ResponseResult.success();
    }

    @PostMapping(value = "/list")
    public ResponseResult<List<JobInfo>> list(@RequestBody JobInfoListDTO jobInfoListDTO) {
        return ResponseResult.success(jobService.list(jobInfoListDTO));
    }


}
