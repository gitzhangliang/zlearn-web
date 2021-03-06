package com.zl.easyexcel.merge;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.zl.easyexcel.merge.CellPoint;
import com.zl.easyexcel.merge.MergeTypeEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文本相同单元格合并，支持行合并和列合并, 行列同事合并
 * Text same cell merge, <p>
 *     support row and column merge, row and column colleagues merge
 * @author Lucas xie
 * @date 2020-1-17
 * @since 1.0.0L
 */
public class TextSameMergeStrategy extends AbstractMergeStrategy {

    /**
     * 临时列存储
     * Temporary col storage
     */
    Map<String, CellPoint> col = new HashMap<String, CellPoint>();

    /**
     * 临时行存储
     * Temporary row storage
     */
    Map<String, CellPoint> row = new HashMap<String, CellPoint>();

    /**
     * 总列数
     * Total number of rows
     */
    private int totalCols;
    /**
     * 总行数
     */
    private int totalRows;

    private int mergeType;

    private boolean mergeAllRow;
    private boolean mergeAllColumn;
    private List<Integer> mergeRows;
    private List<Integer> mergeColumns;

    /**
     * 构造方法传入 总行数、总列数
     * The constructor passes in the total number of rows and columns
     * @param totalCols
     * @param totalRows
     * @param mergeType
     */
    public TextSameMergeStrategy(int totalCols, int totalRows, MergeTypeEnum mergeType) {
        this.totalCols = totalCols;
        this.totalRows = totalRows;
        this.mergeType = mergeType.index;
    }
    public TextSameMergeStrategy(int totalCols, int totalRows, MergeTypeEnum mergeType,
                                 boolean mergeAllRow,boolean mergeAllColumn,List<Integer> mergeRows,List<Integer> mergeColumns) {
        this.totalCols = totalCols;
        this.totalRows = totalRows;
        this.mergeType = mergeType.index;
        this.mergeAllColumn = mergeAllColumn;
        this.mergeAllRow = mergeAllRow;
        this.mergeColumns = mergeColumns;
        this.mergeRows = mergeRows;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, int i) {
        // 行合并
        if (this.mergeType == MergeTypeEnum.HORIZONTAL_MERGE.index) {
            this.horizontalMerge(cell, i, sheet);
        }
        // 列合并
        if (this.mergeType == MergeTypeEnum.VERTICAL_MERGE.index) {
            this.verticalMerge(cell, head, i, sheet);
        }
        // 先行后列合并
        if (this.mergeType == MergeTypeEnum.CENTER_MERGE.index) {
            this.horizontalMerge(cell, i, sheet);
            this.verticalMerge(cell, head, i, sheet);
        }
    }

    /**
     * 水平合并 行合并
     * Horizontal merge row merge
     * @param cell
     * @param index
     * @param sheet
     */
    private void horizontalMerge(Cell cell, Integer index, Sheet sheet){
        if(!mergeAllRow){
            if(!mergeRows.contains(cell.getRowIndex())){
                return;
            }
        }
        String key = sheet.getSheetName()+"R"+index;
        CellPoint rowCellPoint = row.get(key);
        if(rowCellPoint == null){
            row.put(key, new CellPoint());
            rowCellPoint = row.get(key);
        }
        rowCellPoint.setStartY(cell.getRowIndex());
        rowCellPoint.setEndY(cell.getRowIndex());

        if(rowCellPoint.getText() != null && rowCellPoint.getText().equals(getCellText(cell))){
            rowCellPoint.setEndX(cell.getColumnIndex());
            if(this.totalCols-1 == cell.getColumnIndex()){
                this.executorMerge(rowCellPoint, sheet);
            }
        }else{
            if(rowCellPoint.getStartX() < rowCellPoint.getEndX()){
                this.executorMerge(rowCellPoint, sheet);
            }
            rowCellPoint.setStartX(cell.getColumnIndex());
        }
        rowCellPoint.setText(getCellText(cell));
    }

    /**
     * 垂直合并 列合并
     * Vertical merge column merge
     * @param cell 单元格
     * @param head 表头
     * @param index 行索引
     * @param sheet sheet
     */
    public void verticalMerge(Cell cell, Head head, Integer index, Sheet sheet){
        if(!mergeAllColumn){
            if(!mergeColumns.contains(cell.getColumnIndex())){
                return;
            }
        }
        CellPoint cellPoint = col.get(sheet.getSheetName()+"C"+cell.getColumnIndex());
        if(cellPoint == null){
            col.put(sheet.getSheetName()+"C"+cell.getColumnIndex(), new CellPoint());
            cellPoint = col.get(sheet.getSheetName()+"C"+cell.getColumnIndex());
        }
        if(cellPoint.getText() != null && cellPoint.getText().equals(this.getCellText(cell))){
            cellPoint.setEndX(cell.getColumnIndex());
            cellPoint.setEndY(cell.getRowIndex());
            if(index == totalRows-1){
                this.executorMerge(cellPoint, sheet);
            }
        }else{
            if(cellPoint.getStartY() != cellPoint.getEndY()){
                this.executorMerge(cellPoint, sheet);
            }
            cellPoint.setStartX(cell.getColumnIndex());
            cellPoint.setStartY(cell.getRowIndex());
            cellPoint.setEndX(cell.getColumnIndex());
            cellPoint.setEndY(cell.getRowIndex());
        }
        cellPoint.setText(this.getCellText(cell));
    }

    /**
     * 执行合并
     * Perform merge
     * @param sheet sheet的对象
     * @param cellPoint 单元格坐标对象
     */
    private void executorMerge(CellPoint cellPoint, Sheet sheet){
        CellRangeAddress cellRangeAddress = new CellRangeAddress(
                cellPoint.getStartY(),
                cellPoint.getEndY(),
                cellPoint.getStartX(),
                cellPoint.getEndX());
        sheet.addMergedRegionUnsafe(cellRangeAddress);
    }

    /**
     * 获取单元格的内容，转为字符串
     * Get the contents of a cell and convert it to a string
     * @param cell
     * @return
     */
    private String getCellText(Cell cell){
        String text = "";
        CellType cellType = cell.getCellTypeEnum();
        if(CellType.BOOLEAN.equals(cellType)){
            text = Boolean.toString(cell.getBooleanCellValue());
        }else if(CellType.NUMERIC.equals(cellType)){
            text = Double.toString(cell.getNumericCellValue());
        }else if(CellType.STRING.equals(cellType)){
            text = cell.getStringCellValue();
        }else if(cell.getCellTypeEnum().equals(Date.class)){
            text = cell.getDateCellValue().toString();
        }
        return text;
    }
}