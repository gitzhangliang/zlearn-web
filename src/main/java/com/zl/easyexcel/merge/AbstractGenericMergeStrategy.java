package com.zl.easyexcel.merge;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**合并单元格的抽象基类。对于每个单元格都会根据GenericMergeFunction返回的GenericMergeBO判断是否需要合并
 * @author zhangliang
 * @date 2020/5/25.
 */
public abstract class AbstractGenericMergeStrategy extends AbstractMergeStrategy {

    protected int headRowNumber = 1;

    protected GenericMergeFunction function;

    protected AbstractGenericMergeStrategy(int headRowNumber){
        this.headRowNumber = headRowNumber;
    }
    public AbstractGenericMergeStrategy(int headRowNumber,GenericMergeFunction function) {
        this.headRowNumber = headRowNumber;
        this.function = function;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        GenericMergeFunction.GenericMergeFunctionHelper helper = new GenericMergeFunction.GenericMergeFunctionHelper();
        helper.setColumnIndex(columnIndex);
        helper.setRowIndex(rowIndex);
        helper.setRelativeRowIndex(relativeRowIndex);
        GenericMergeBO bo =function.apply(helper);
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
