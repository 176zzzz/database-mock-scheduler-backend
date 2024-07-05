package com.zp.controller;

import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsHandle;
import com.zp.model.common.PageResult;
import com.zp.model.common.ResponseResult;
import com.zp.model.dto.DsListDto;
import com.zp.model.entity.DataSource;
import com.zp.service.DataSourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * DsController
 *
 * @author ZP
 * @since 2024/5/30 8:50
 */
@RestController
@RequestMapping("/ds")
public class DsController {

    @Resource
    private DataSourceService dataSourceService;

    @Resource
    private DsHandle dsHandle;

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody DataSource dataSource) {
        return ResponseResult.success(dataSourceService.save(dataSource));
    }

    @GetMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestParam Long id) {
        return ResponseResult.success(dataSourceService.removeById(id));
    }

    @PostMapping("update")
    public ResponseResult<Boolean> update(@RequestBody DataSource dataSource) {
        return ResponseResult.success(dataSourceService.updateById(dataSource));
    }

    @PostMapping("/list")
    public ResponseResult<PageResult<DataSource>> list(@RequestBody DsListDto dsListDto) {
        return ResponseResult.success(dataSourceService.getList(dsListDto.getDrive(), dsListDto.getPageSize(),
            dsListDto.getPageNum()));
    }

    @GetMapping("setDs")
    public ResponseResult<Boolean> setDs(@RequestParam Long id) throws ServiceException {
        return ResponseResult.success(dataSourceService.setDs(id));
    }

    @GetMapping("resetDefaultDs")
    public ResponseResult<Boolean> resetDefaultDs() throws ServiceException {
        dsHandle.resetDefaultDs();
        return ResponseResult.success();
    }


}
