package com.zl.easyexcel.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**行合并
 * @author zhangliang
 * @date 2020/5/25.
 */
public class GenericRowMergeStrategy extends AbstractGenericMergeStrategy{


    public GenericRowMergeStrategy(int headRowNumber) {
        super(headRowNumber);
    }
    public GenericRowMergeStrategy(int headRowNumber, GenericMergeFunction function) {
        super(headRowNumber,function);
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, GenericMergeBO bo) {
        if (Boolean.TRUE.equals(bo.getStartMergeCell())) {
            CellPoint cellPoint = new CellPoint();
            cellPoint.setStartX(cell.getColumnIndex());
            cellPoint.setStartY(cell.getRowIndex());
            cellPoint.setEndX(cell.getColumnIndex());
            cellPoint.setEndY(cell.getRowIndex() + bo.getRowspan() - 1);
            cellPoint.setText(getCellText(cell));
            if (cellPoint.getStartY() != cellPoint.getEndY()) {
                executorMerge(cellPoint, sheet);
            }
        }
    }
}
