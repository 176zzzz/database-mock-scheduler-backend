package com.zp.controller;

import com.zp.common.exception.ServiceException;
import com.zp.core.ds.DsInfoProvider;
import com.zp.model.common.ResponseResult;
import com.zp.model.domain.TableColumnInfo;
import com.zp.model.domain.TableInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * TableController
 *
 * @author ZP
 * @since 2024/5/30 10:09
 */
@RestController
@RequestMapping("table")
public class TableController {

    @Resource
    private DsInfoProvider dsInfoProvider;

    @GetMapping("tables")
    public ResponseResult<List<TableInfo>> tables() throws ServiceException {
        return ResponseResult.success(dsInfoProvider.getTables());
    }

    @GetMapping("/tableColumns")
    public ResponseResult<List<TableColumnInfo>> tableColumns(@RequestParam String tableName) throws ServiceException {
        return ResponseResult.success(dsInfoProvider.getTableColumns(tableName));
    }
}
