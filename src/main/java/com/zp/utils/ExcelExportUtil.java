package com.zp.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Office导出工具类
 *
 * @author ZP
 * @date 2019/11/04
 **/
@Slf4j
public class ExcelExportUtil {

    /** 允许导出的最大条数 */
    private static final Integer EXPORT_EXCEL_MAX_NUM = 100000;

    /**
     * @param file         导入文件
     * @param importParams 设置数据格式，开始列位置等，例如importParams.setHeadRows(1);importParams.setTitleRows(1);
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz, ImportParams importParams) {
        try {
            List<T> list = ExcelImportUtil.importExcel(file.getInputStream(), clazz, importParams);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出Excel
     *
     * @param workbook workbook流
     * @param fileName 文件名
     * @param response 响应
     */
    public static void exportExcel(Workbook workbook, String fileName, HttpServletResponse response) {
        //给文件名拼接上日期
        fileName = fileName + StrUtil.UNDERLINE + DateUtil.today();
        //输出文件
        try (OutputStream out = response.getOutputStream()) {
            //获取文件名并转码
            String name = URLEncoder.encode(fileName, "UTF-8");
            //编码
            response.setCharacterEncoding("UTF-8");
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xlsx");
            //输出表格
            workbook.write(out);
        } catch (IOException e) {
            log.error("文件导出异常,详情如下:", e);
        } finally {
            try {
                if (workbook != null) {
                    //关闭输出流
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("文件导出异常,详情如下:", e);
            }
        }
    }

    /**
     * 获取导出的 Workbook对象
     *
     * @param title     大标题
     * @param sheetName 页签名
     * @param object    导出实体
     * @param list      数据集合
     * @return Workbook
     */
    public static Workbook getWorkbook(String title, String sheetName, Class<?> object, List<?> list) {
        //判断导出数据是否为空
        if (list == null) {
            list = new ArrayList<>();
        }
        //判断导出数据数量是否超过限定值
        if (list.size() > EXPORT_EXCEL_MAX_NUM) {
            title = "导出数据行数超过:" + EXPORT_EXCEL_MAX_NUM + "条,无法导出、请添加导出条件!";
            list = new ArrayList<>();
        }
        //获取导出参数
        ExportParams exportParams = new ExportParams(title, sheetName, ExcelType.XSSF);
        //设置导出样式
        exportParams.setStyle(ExcelStyleUtil.class);
        //设置行高
        exportParams.setHeight((short) 7);
        //输出Workbook流
        Workbook workbook = cn.afterturn.easypoi.excel.ExcelExportUtil.exportExcel(exportParams, object, list);
        //设置自适应列宽
        Sheet sheet = workbook.getSheetAt(0);
        setSizeColumn(sheet);
        return workbook;
    }

    /**
     * 获取导出的 Workbook对象
     *
     * @param path 模板路径
     * @param map  导出内容map
     * @return Workbook
     */
    public static Workbook getWorkbook(String path, Map<String, Object> map) {
        //获取导出模板
        TemplateExportParams params = new TemplateExportParams(path);
        //设置导出样式
        params.setStyle(ExcelStyleUtil.class);
        //输出Workbook流
        return cn.afterturn.easypoi.excel.ExcelExportUtil.exportExcel(params, map);
    }

    /**
     * 自适应宽度,高度(中文支持)
     * @param sheet Sheet
     */
    private static void setSizeColumn(Sheet sheet) {
        log.info("设置自适应列宽");
        int maxColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < maxColumn; i++) {
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < maxColumn; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int maxWith = 256 * 255;
            // 解决自动设置列宽中文失效的问题
            if (sheet.getColumnWidth(i) < maxWith / 1.7) {
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
            }
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);

            //限制下最大宽度
            if (newWidth > maxWith) {
                sheet.setColumnWidth(i, maxWith);
            } else if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }

        // 设置自适应行高
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            setRowHeight(row);
        }

    }


    private static void setRowHeight(Row row) {
        //根据内容长度设置行高
        int enterCnt = 0;
        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
            int rwsTemp = row.getCell(j).toString().length();
            //这里取每一行中的每一列字符长度最大的那一列的字符
            if (rwsTemp > enterCnt) {
                enterCnt = rwsTemp;
            }
        }
        //设置默认行高
        int maxRowHeight = 20;
        int maxCnt = 65;
        row.setHeightInPoints(maxRowHeight);
        //如果字符长度大于35，判断大了多少倍，根据倍数来设置相应的行高
        if (enterCnt > maxCnt) {
            float d = enterCnt / maxCnt;
            float f = maxRowHeight * d;
            row.setHeightInPoints((float) (f * 1.2));
        }
    }
}