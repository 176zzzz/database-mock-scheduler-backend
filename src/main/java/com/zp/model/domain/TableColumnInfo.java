package com.zp.model.domain;

import lombok.Data;

/**
 * TableColumnInfo
 *
 * @author ZP
 * @since 2024/5/29 16:07
 */
@Data
public class TableColumnInfo {

    private String name;

    private String type;

    private Integer length;

    private String comment;

    private String indexType;
}
