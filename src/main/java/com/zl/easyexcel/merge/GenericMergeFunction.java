package com.zl.easyexcel.merge;

import lombok.Data;

/**
 * @author tzxx
 */
public interface GenericMergeFunction{
    GenericMergeBO apply(GenericMergeFunctionHelper helper);

    @Data
    class GenericMergeFunctionHelper {
        private int columnIndex;
        private int rowIndex;
        private int relativeRowIndex;
    }
}