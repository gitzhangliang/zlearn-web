package com.zl.easyexcel.write;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import com.zl.easyexcel.write.style.GenericCellStyleFunction;
import com.zl.easyexcel.write.style.GenericStyleFunction;
import com.zl.easyexcel.write.style.GenericStyleFunctionHelper;
import com.zl.easyexcel.write.style.GenericStyleResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2020/11/11.
 */
public class AppointWidthHeightAndCellStyleStrategy extends AbstractCellWriteHandler {

    private final Map<Integer,Integer> columnWidthMap ;
    private final Map<Integer,Short> rowHeightMap ;
    private final Map<Integer,Short> relativeRowHeightMap ;

    private final GenericCellStyleFunction genericCellStyleFunction;

    /**
     * 上边四个的整合  且更灵活
     */
    private final GenericStyleFunction genericStyleFunction;

    public AppointWidthHeightAndCellStyleStrategy(Map<Integer,Integer> columnWidthMap, Map<Integer,Short> rowHeightMap,
                                                  Map<Integer,Short> relativeRowHeightMap,
                                                  GenericCellStyleFunction genericCellStyleFunction,
                                                  GenericStyleFunction genericStyleFunction){
        this.columnWidthMap = columnWidthMap;
        this.rowHeightMap = rowHeightMap;
        this.relativeRowHeightMap = relativeRowHeightMap;
        this.genericCellStyleFunction = genericCellStyleFunction;
        this.genericStyleFunction = genericStyleFunction;
    }


    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        GenericStyleFunctionHelper helper = new GenericStyleFunctionHelper();
        helper.setWorkbook(cell.getSheet().getWorkbook());
        helper.setRelativeRowIndex(relativeRowIndex);
        helper.setColumnIndex(columnIndex);
        helper.setRowIndex(rowIndex);
        if (Boolean.FALSE.equals(isHead)) {
            if (genericCellStyleFunction != null) {
                CellStyle apply = genericCellStyleFunction.apply(helper);
                if (apply != null) {
                    cell.setCellStyle(apply);
                }
            }
            if (columnWidthMap != null && columnWidthMap.containsKey(cell.getColumnIndex())) {
                writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidthMap.get(cell.getColumnIndex()) * 256);
            }
            Row row = cell.getRow();
            if (rowHeightMap != null && rowHeightMap.containsKey(row.getRowNum())) {
                row.setHeight(rowHeightMap.get(row.getRowNum()));
            }
            if (relativeRowHeightMap != null && relativeRowHeightMap.containsKey(relativeRowIndex)) {
                row.setHeight(relativeRowHeightMap.get(relativeRowIndex));
            }
            if (genericStyleFunction != null) {
                GenericStyleResult genericStyleResult = genericStyleFunction.apply(helper);
                if (genericStyleResult != null) {
                    CellStyle cellStyle = genericStyleResult.getCellStyle();
                    if (cellStyle != null) {
                        cell.setCellStyle(cellStyle);
                    }
                    Integer cellWidth = genericStyleResult.getCellWidth();
                    if (cellWidth != null) {
                        writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), cellWidth * 256);
                    }
                    Short rowHeight = genericStyleResult.getRowHeight();
                    if (rowHeight != null) {
                        row.setHeight(rowHeight);
                    }
                    Short relativeRowHeight = genericStyleResult.getRelativeRowHeight();
                    if (relativeRowHeight != null) {
                        row.setHeight(relativeRowHeight);
                    }
                }
            }
        }
    }
}
