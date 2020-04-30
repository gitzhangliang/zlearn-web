package com.zl.easyexcel;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author zl
 * @date 2019/12/17.
 */
@Data
public class ExcelCellBo {
    private Field field;
    private String fieldName;
    private String headName;
    private Integer columnIndex;
    private Integer rowIndex;
}
