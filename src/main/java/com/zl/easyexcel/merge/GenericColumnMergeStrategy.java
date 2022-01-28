package com.zl.easyexcel.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**列合并
 * @author zhangliang
 * @date 2020/5/25.
 */
public class GenericColumnMergeStrategy extends AbstractGenericMergeStrategy {


    public GenericColumnMergeStrategy(int headRowNumber) {
        super(headRowNumber);
    }

    public GenericColumnMergeStrategy(int headRowNumber, GenericMergeFunction function) {
        super(headRowNumber,function);
    }
    @Override
    protected void merge(Sheet sheet, Cell cell, GenericMergeBO bo) {
        //列合并
        if (Boolean.TRUE.equals(bo.getStartMergeCell())) {
            CellPoint cellPoint = new CellPoint();
            cellPoint.setStartY(cell.getRowIndex());
            cellPoint.setEndY(cell.getRowIndex());
            cellPoint.setStartX(cell.getColumnIndex());
            cellPoint.setEndX(cell.getColumnIndex() + bo.getColspan() - 1);
            cellPoint.setText(getCellText(cell));
            if (cellPoint.getStartX() != cellPoint.getEndX()) {
                executorMerge(cellPoint, sheet);
            }
        }
    }
}
