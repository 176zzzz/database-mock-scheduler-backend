package com.zp.service.impl;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zp.common.constant.ExceptionMsgConstant;
import com.zp.common.exception.ServiceException;
import com.zp.mapper.DicMapper;
import com.zp.model.entity.Dic;
import com.zp.model.entity.DicDetail;
import com.zp.service.DicDetailService;
import com.zp.service.DicService;
import com.zp.utils.ExcelExportUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator-3.5.1
 * @since 2024-05-22 02:40:56
 */
@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dic> implements DicService {

    @Resource
    private DicDetailService dicDetailService;

    @Override
    public String getValueById(Long id) throws ServiceException {
        Dic dic = this.getById(id);
        if (dic == null) {
            throw new RuntimeException(ExceptionMsgConstant.DIC_NOT_FOUND);
        }
        List<DicDetail> dicDetailList = dicDetailService.getListByDicId(id);
        if (CollectionUtils.isEmpty(dicDetailList)) {
            throw new ServiceException(ExceptionMsgConstant.NOT_FOUND_STRATEGY);
        }
        Random random = new Random();
        DicDetail dicDetail = dicDetailList.get(random.nextInt(dicDetailList.size()));
        return dicDetail.getValue();
    }

    @Override
    public List<Dic> list(String name, String type) {
        return this.list(Wrappers.<Dic>lambdaQuery()
            .like(StringUtils.isNotEmpty(name), Dic::getName, name)
            .eq(StringUtils.isNotEmpty(type), Dic::getType, type));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        this.removeById(id);
        List<DicDetail> dicDetailList = dicDetailService.getListByDicId(id);
        dicDetailService.removeBatchByIds(dicDetailList);
        return true;
    }

    @Override
    public Boolean exportByDicId(Long dicId, HttpServletResponse response) throws ServiceException {
        List<DicDetail> dicDetailList = dicDetailService.getListByDicId(dicId);
        if (CollectionUtils.isEmpty(dicDetailList)) {
            throw new ServiceException(ExceptionMsgConstant.DICDETAIL_NULL);
        }
        Dic dic = this.getById(dicId);
        if (dic == null) {
            throw new ServiceException(ExceptionMsgConstant.DIC_NOT_FOUND);
        }
        Workbook workbook = ExcelExportUtil.getWorkbook(dic.getName() + "数据字典", dic.getName(), DicDetail.class,
            dicDetailList);
        ExcelExportUtil.exportExcel(workbook, "数据字典详情", response);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importDetailList(MultipartFile file, Long dicId) throws Exception {
        Dic dic = this.getById(dicId);
        if (dic == null) {
            throw new ServiceException(ExceptionMsgConstant.DIC_NOT_FOUND);
        }
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        List<DicDetail> convertAfterList = ExcelExportUtil.importExcel(file,
            DicDetail.class, importParams);
        if (CollectionUtils.isEmpty(convertAfterList)) {
            throw new ServiceException(ExceptionMsgConstant.IMPORT_FAIL);
        }
        convertAfterList.forEach(dicDetail -> dicDetail.setDicId(dicId));
        dicDetailService.saveBatch(convertAfterList);
        return true;
    }

}
