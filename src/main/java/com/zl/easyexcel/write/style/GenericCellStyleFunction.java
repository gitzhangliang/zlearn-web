package com.zl.easyexcel.write.style;

import org.apache.poi.ss.usermodel.CellStyle;

public interface GenericCellStyleFunction {
    CellStyle apply(GenericStyleFunctionHelper helper);

}
