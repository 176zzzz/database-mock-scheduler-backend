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

    String getValueById(Long id) throws ServiceException;

    List<Dic> list(String name, String type);

    Boolean delete(Long id);

    Boolean exportByDicId(Long dicId, HttpServletResponse response) throws ServiceException;

    Boolean importDetailList(MultipartFile file, Long dicId) throws Exception;


}
