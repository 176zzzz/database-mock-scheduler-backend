package com.zp.model.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * PageResult
 *
 * @author ZP
 * @since 2024/5/30 9:10
 */
@Data
@Builder
public class PageResult<T> {

    private Long total;

    private List<T> data;


}
