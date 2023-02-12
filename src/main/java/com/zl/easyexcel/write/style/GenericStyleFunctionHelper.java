package com.zl.easyexcel.write.style;

import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

@Data
public class GenericStyleFunctionHelper {
    private int columnIndex;
    private int rowIndex;
    private int relativeRowIndex;
    private Workbook workbook;
}