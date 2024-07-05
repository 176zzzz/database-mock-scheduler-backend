package com.zp.model.enums;

/**
 * ColumnType
 *
 * @author ZP
 * @since 2024/5/31 9:33
 */
public enum ColumnType {

    /**
     *  数值型
     */
    INTEGER("INTEGER"),
    DECIMAL("DECIMAL"),
    INT("INT"),
    BIGINT("BIGINT"),
    NUMBER("NUMBER"),

    /**
     * 字符串
     */
    VARCHAR("VARCHAR"),
    CHAR("CHAR"),
    TEXT("TEXT"),

    /**
     *  日期
     */
    DATE("DATE"),
    TIME("TIME"),
    TIMESTAMP("TIMESTAMP"),

    /**
     * 其他
     */
    BLOB("BLOB"),
    OTHER("OTHER");

    private final String type;

    ColumnType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ColumnType getType(String type){
       for (ColumnType columnType : ColumnType.values()){
           if (columnType.type.equalsIgnoreCase(type)){
               return columnType;
           }
       }
       return OTHER;
    }
}
