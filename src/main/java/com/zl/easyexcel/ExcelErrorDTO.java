package com.zl.easyexcel;

import lombok.Data;

/**
 * @author zl
 * @date 2019/11/15.
 */
@Data
public class ExcelErrorDTO {
    private String value;
    private Integer columnIndex;
    private Integer rowIndex;
    private String headName;
    private String errMsg;
}
