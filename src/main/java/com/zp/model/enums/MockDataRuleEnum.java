package com.zp.model.enums;

/**
 * MockDataType
 *
 * @author ZP
 * @since 2024/5/17 15:51
 */
public enum MockDataRuleEnum {

    FIXED("FIXED","固定值"),

    INTEGER("INTEGER","整数"),

    DECIMAL("DECIMAL","小数"),

    DATE("DATE","日期"),

    DIC("DIC","数据字典");

    private String code;

    private String name;

    private MockDataRuleEnum(String code,String name){
        this.code=code;
        this.name=name;
    }

}
