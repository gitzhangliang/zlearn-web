package com.zl.easyexcel.write.style;

import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

@Data
public class GenericStyleResult {
    private Short rowHeight;
    private Short relativeRowHeight;
    private Integer cellWidth;
    private CellStyle cellStyle;
}