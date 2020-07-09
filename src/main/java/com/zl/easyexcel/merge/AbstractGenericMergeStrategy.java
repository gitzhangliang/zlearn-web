package com.zl.easyexcel.merge;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * @author zhangliang
 * @date 2020/5/25.
 */
public abstract class AbstractGenericMergeStrategy<T extends GenericMerge> extends AbstractMergeStrategy {
    protected List<T> list;
    protected int headRowNumber = 1;

    protected AbstractGenericMergeStrategy(List<T> list){
        this.list = list;
    }
    protected AbstractGenericMergeStrategy(List<T> list, int headRowNumber){
        this.list = list;
        this.headRowNumber = headRowNumber;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        T t = list.get(rowIndex - headRowNumber);
        GenericMergeBO bo = t.merge(columnIndex);
        if (bo != null){
            merge(sheet,cell,bo);
        }
    }

    protected abstract void merge(Sheet sheet, Cell cell,GenericMergeBO bo);

    protected void executorMerge(CellPoint cellPoint, Sheet sheet){
            CellRangeAddress cellRangeAddress = new CellRangeAddress(
                    cellPoint.getStartY(),
                    cellPoint.getEndY(),
                    cellPoint.getStartX(),
                    cellPoint.getEndX());
            sheet.addMergedRegionUnsafe(cellRangeAddress);
    }

    String getCellText(Cell cell){
        String text = "";
        CellType cellType = cell.getCellTypeEnum();
        if(CellType.STRING.equals(cellType)){
            text = cell.getStringCellValue();
        }else if(CellType.NUMERIC.equals(cellType)){
            text = Double.toString(cell.getNumericCellValue());
        }else if(CellType.BOOLEAN.equals(cellType)){
            text = Boolean.toString(cell.getBooleanCellValue());
        }
        return text;
    }
}
