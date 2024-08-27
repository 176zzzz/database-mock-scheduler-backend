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

    /**
     * getListByDicId 根据id获取字典详情列表
     * @param dicId Long
     * @return List<DicDetail>
     */
    List<DicDetail> getListByDicId(Long dicId);

}
