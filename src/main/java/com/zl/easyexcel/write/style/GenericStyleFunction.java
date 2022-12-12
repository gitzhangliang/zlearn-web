package com.zl.easyexcel.write.style;

import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author tzxx
 */
public interface GenericStyleFunction {
    CellStyle apply(GenericStyleFunctionHelper helper);

    @Data
    class GenericStyleFunctionHelper {
        private int columnIndex;
        private int rowIndex;
        private int relativeRowIndex;
        private Workbook workbook;
    }
}