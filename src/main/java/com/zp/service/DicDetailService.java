package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.model.entity.DicDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-22 02:40:57
 */
public interface DicDetailService extends IService<DicDetail> {

    List<DicDetail> getListByDicId(Long dicId);

}
