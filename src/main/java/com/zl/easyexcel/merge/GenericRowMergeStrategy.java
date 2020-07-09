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
public class GenericRowMergeStrategy<T extends GenericMerge> extends AbstractGenericMergeStrategy<T> {
    private Map<String, CellPoint> col = new HashMap<>();

    public GenericRowMergeStrategy(List<T> list) {
        super(list);
    }

    public GenericRowMergeStrategy(List<T> list, int headRowNumber) {
        super(list, headRowNumber);
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, GenericMergeBO bo) {
        if (Boolean.TRUE.equals(bo.getStartMergeCell())) {
            CellPoint cellPoint = col.get(sheet.getSheetName() + "C" + cell.getColumnIndex());
            if (cellPoint == null) {
                cellPoint = new CellPoint();
                cellPoint.setStartX(cell.getColumnIndex());
                cellPoint.setStartY(cell.getRowIndex());
                cellPoint.setEndX(cell.getColumnIndex());
                cellPoint.setEndY(cell.getRowIndex() + bo.getRowspan() - 1);
                cellPoint.setText(getCellText(cell));
                col.put(sheet.getSheetName() + "C" + cell.getColumnIndex(), cellPoint);
            } else {
                if (cellPoint.getStartY() != cellPoint.getEndY()) {
                    executorMerge(cellPoint, sheet);
                }
                cellPoint.setStartX(cell.getColumnIndex());
                cellPoint.setStartY(cell.getRowIndex());
                cellPoint.setEndX(cell.getColumnIndex());
                cellPoint.setEndY(cell.getRowIndex() + bo.getRowspan() - 1);
            }
        }
    }
}
