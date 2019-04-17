package com.zl.model.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzxx
 */
public class SheetOutput {

    public void outputExcel(List<Sheet> sheetList, Workbook wb) {
        for (Sheet sheet : sheetList) {
            org.apache.poi.ss.usermodel.Sheet xsSheet = wb.createSheet("aa");
            for (int i = 0; i < sheet.getMaxRowSize(); i++) {
                for (SubTable subTab : sheet) {
                    List<Cell> cells = subTab.getCellsByRowIndex(i);
                    if (cells.size() == 0) {
                        continue;
                    }
                    for (int x = 0; x < cells.size(); x++) {
                        outputCell(xsSheet, i, cells.get(x));
                    }
                }
            }
        }
    }

    private void outputCell(org.apache.poi.ss.usermodel.Sheet sheet, int rowStart, Cell cell) {
        List<org.apache.poi.ss.usermodel.Cell> cells = new ArrayList<>();
        for (int i = 0; i < cell.getRowSpan(); i++) {
            org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowStart + i);
            for (int j = 0; j < cell.getColSpan(); j++) {
                org.apache.poi.ss.usermodel.Cell xssfCell = row.createCell(row.getPhysicalNumberOfCells());
                if (cell.getValue() == null) {
                    xssfCell.setCellValue(cell.getValueDouble());
                } else {
                    xssfCell.setCellValue(( String ) cell.getValue());
                }
                if (cell.getStyle() != null) {
                    xssfCell.setCellStyle(cell.getStyle());
                }
                if (cell.getColumnWidth() != null) {
                    sheet.setColumnWidth(xssfCell.getColumnIndex(), cell.getColumnWidth() * 255);
                }
                cells.add(xssfCell);
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(cells.get(0).getRowIndex(), cells.get(cells.size() - 1).getRowIndex(), cells.get(0).getColumnIndex(), cells.get(cells.size() - 1).getColumnIndex()));
    }

    public void outputHTML(Sheet sheet) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (int i = 0; i < sheet.getMaxRowSize(); i++) {
            sb.append("<tr>");
            for (SubTable subTab : sheet) {
                List<Cell> cells = subTab.getCellsByRowIndex(i);
                if (cells.size() == 0) {
                    continue;
                }
                for (Cell cell : cells) {
                    outputHTMLCell(sb, cell);
                }
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        System.out.println(sb);
    }

    private void outputHTMLCell(StringBuilder sb, Cell cell) {
        sb.append("<th rowspan='" + cell.getRowSpan() + "' colspan='" + cell.getColSpan() + "'>");
        sb.append(cell.getValue());
        sb.append("</th>");
    }
}
