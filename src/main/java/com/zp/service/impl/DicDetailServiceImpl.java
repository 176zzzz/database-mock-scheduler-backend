package com.zp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.mapper.DicDetailMapper;
import com.zp.model.entity.DicDetail;
import com.zp.service.DicDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-22 02:40:57
 */
@Service
public class DicDetailServiceImpl extends ServiceImpl<DicDetailMapper, DicDetail> implements DicDetailService {

    @Override
    public List<DicDetail> getListByDicId(Long dicId){
        return this.list(Wrappers.<DicDetail>lambdaQuery().eq(DicDetail::getDicId,dicId));
    }

}
