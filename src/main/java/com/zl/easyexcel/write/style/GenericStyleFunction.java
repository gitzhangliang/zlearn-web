package com.zl.easyexcel.write.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author tzxx
 */
public interface GenericStyleFunction {
    GenericStyleResult apply(GenericStyleFunctionHelper helper);

}