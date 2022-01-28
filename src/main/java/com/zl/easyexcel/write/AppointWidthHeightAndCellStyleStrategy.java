package com.zl.easyexcel.write;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhangliang
 * @date 2020/11/11.
 */
public class AppointWidthHeightAndCellStyleStrategy extends AbstractCellWriteHandler {

    private final Map<Integer,Integer> columnWidthMap ;
    private final Map<Integer,Short> rowHeightMap ;
    private final Map<Integer,Short> relativeRowHeightMap ;
    private final Function<Workbook,Map<Integer, CellStyle>> cellStyleFunction;
    public AppointWidthHeightAndCellStyleStrategy(Map<Integer,Integer> columnWidthMap, Map<Integer,Short> rowHeightMap,
                                                  Map<Integer,Short> relativeRowHeightMap,
                                                  Function<Workbook,Map<Integer, CellStyle>> cellStyleFunction){
        this.columnWidthMap = columnWidthMap;
        this.rowHeightMap = rowHeightMap;
        this.relativeRowHeightMap = relativeRowHeightMap;
        this.cellStyleFunction = cellStyleFunction;
    }


    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (Boolean.FALSE.equals(isHead)) {
            if (cellStyleFunction != null) {
                Map<Integer, CellStyle> cellStyleMap = cellStyleFunction.apply(cell.getSheet().getWorkbook());
                CellStyle cellStyle = cellStyleMap.get(cell.getColumnIndex());
                if (cellStyle != null) {
                    cell.setCellStyle(cellStyle);
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
        }
    }
}
