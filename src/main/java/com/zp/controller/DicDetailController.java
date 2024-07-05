package com.zp.controller;

import com.zp.model.common.ResponseResult;
import com.zp.model.entity.DicDetail;
import com.zp.service.DicDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZP
 * @since 2024/5/28 16:09
 */
@RestController()
@RequestMapping("/dicDetail")
public class DicDetailController {

    @Resource
    private DicDetailService dicDetailService;

    @GetMapping("/listByDicId")
    public ResponseResult<List<DicDetail>> list(@RequestParam Long dicId) {
        return ResponseResult.success(dicDetailService.getListByDicId(dicId));
    }

    @PostMapping("/save")
    public ResponseResult<Boolean> save(@RequestBody DicDetail dicDetail) {
        return ResponseResult.success(dicDetailService.save(dicDetail));
    }

    @GetMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestParam Long id) {
        return ResponseResult.success(dicDetailService.removeById(id));
    }

    @PostMapping("/update")
    public ResponseResult<Boolean> update(@RequestBody DicDetail dicDetail) {
        return ResponseResult.success(dicDetailService.updateById(dicDetail));
    }


}
