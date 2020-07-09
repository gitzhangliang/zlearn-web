package com.zl.easyexcel.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2020/5/25.
 */
public class GenericColumnMergeStrategy<T extends GenericMerge> extends AbstractGenericMergeStrategy<T> {
    private Map<String, CellPoint> col = new HashMap<>();

    public GenericColumnMergeStrategy(List<T> list) {
        super(list);
    }

    public GenericColumnMergeStrategy(List<T> list, int headRowNumber) {
        super(list, headRowNumber);
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, GenericMergeBO bo) {
    }
}
