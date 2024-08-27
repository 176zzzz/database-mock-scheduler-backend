package com.zp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zp.common.exception.ServiceException;
import com.zp.model.entity.Dic;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-22 02:40:56
 */
public interface DicService extends IService<Dic> {

    /**
     * getValueById 根据字典id随机生成一个值(从字典对应列表里选)
     * @param id Long
     * @return String
     */
    String getValueById(Long id) throws ServiceException;

    /**
     * list 字典列表查询
     * @param name String
     * @param type String
     * @return List<Dic>
     */
    List<Dic> list(String name, String type);

    /**
     * delete
     * @param id Long
     * @return Boolean
     */
    Boolean delete(Long id);

    /**
     * exportByDicId
     * @param dicId Long
     * @param response HttpServletResponse
     * @return Boolean
     */
    Boolean exportByDicId(Long dicId, HttpServletResponse response) throws ServiceException;

    /**
     * importDetailList
 * @param file MultipartFile
 * @param dicId Long
 * @return Boolean
     */
    Boolean importDetailList(MultipartFile file, Long dicId) throws Exception;


}
