package com.zp.model.enums;

/**
 * MockDataType
 *
 * @author ZP
 * 
 */
public enum MockDataRuleEnum {
    /**
     * 固定值
     */
    FIXED("FIXED","固定值"),

    INTEGER("INTEGER","整数"),

    DECIMAL("DECIMAL","小数"),

    DATE("DATE","日期"),

    FUN("FUN","函数"),

    DIC("DIC","数据字典"),

    /**
     * 其他
     */
    ADDRESS("ADDRESS","地址"),

    NAME("NAME","名称"),

    TELEPHONE("TELEPHONE","电话");

    private final String code;

    private final String name;

    private MockDataRuleEnum(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getCode(){
        return this.code;
    }

}
