package com.zp.controller;

import com.zp.common.exception.ServiceException;
import com.zp.model.common.ResponseResult;
import com.zp.model.entity.Dic;
import com.zp.service.DicService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * DicController
 *
 * @author ZP
 * @since 2024/5/28 16:09
 */
@RestController()
@RequestMapping("/dic")
public class DicController {

    @Resource
    private DicService dicService;

    @GetMapping("/list")
    public ResponseResult<List<Dic>> getDicDetailById(@RequestParam String name, @RequestParam String type) {
        return ResponseResult.success(dicService.list(name, type));
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody Dic dic) {
        return ResponseResult.success(dicService.save(dic));
    }

    @GetMapping("delete")
    public ResponseResult<Boolean> delete(@RequestParam Long id) {
        return ResponseResult.success(dicService.delete(id));
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody Dic dic) {
        return ResponseResult.success(dicService.updateById(dic));
    }

    @GetMapping("exportByDicId")
    public ResponseResult<Boolean> exportByDicId(@RequestParam Long id, HttpServletResponse response) throws ServiceException {
        return ResponseResult.success(dicService.exportByDicId(id, response));
    }

    @PostMapping("/import")
    public ResponseResult<Boolean> importDetailList(@RequestParam("file") MultipartFile file, @RequestParam Long id) throws Exception {
        return ResponseResult.success(dicService.importDetailList(file, id));
    }


}
