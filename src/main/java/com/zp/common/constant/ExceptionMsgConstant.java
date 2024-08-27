package com.zp.common.constant;

/**
 * BizConstant
 *
 * @author ZP
 * 
 */
public class ExceptionMsgConstant {
    /**
     * 业务报错
     */
    public static final String NOT_COLUMN_INFO = "目标表字段生成策略为空";

    public static final String NOT_CODE = "找不到任务或此任务已开启";

    public static final String NOT_CODE_LIST = "已开启的任务为空";

    public static final String NOT_CODE_STOP = "找不到此任务或此任务已停止";

    public static final String NOT_STOP_INACTIVATED = "不能删除正在运行的任务";

    public static final String NOT_JOB = "对应任务不存在";

    public static final String NOT_COLUMN_NULL = "该列未定义生成策略";

    public static final String NOT_FOUND_STRATEGY = "找不到对应策略";

    public static final String NOT_FIXED_VALUE = "请输入对应固定值";

    public static final String STRATEGY_INTEGER_NULL = "最大值或最小值不能为空且最大值需大于最小值";

    public static final String STRATEGY_LENGTH_NULL = "小数位不能为空";

    public static final String STRATEGY_DATE_NULL = "最小时间和最大时间不能为空";

    public static final String DIC_NOT_FOUND = "未找到对应字典";

    public static final String FUN_NULL = "函数为空";

    public static final String DIC_DETAIL_NULL = "数据字典为空";

    public static final String IMPORT_FAIL = "导入失败，数据字典为空";

    public static final String TABLE_FAIL = "获取数据库表信息失败";

    public static final String TABLE_COLUMNS_FAIL = "获取数据库表数据列信息失败";

    public static final String TABLE_COLUMN_FAIL = "获取数据库表指定数据列信息失败";

    /**
     *  Ds
     */
    public static final String DS_NOT_FOUND = "找不到对应的数据源";

    public static final String DS_TARGET_NULL = "未定义target数据源";

    /**
     * 时间类型
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     *  Job执行
     */
    public static final String JOB_EXECUTE_FAIL = "job执行失败，quartz调度器未传必须参数";
}
