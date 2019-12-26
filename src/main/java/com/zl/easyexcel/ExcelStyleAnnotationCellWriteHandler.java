package com.zl.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.zl.easyexcel.annotation.ExcelStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;

/**
 * @author zhangliang
 * @date 2019/12/19.
 */
@Slf4j
public class ExcelStyleAnnotationCellWriteHandler extends HorizontalCellStyleStrategy {

    private Class c;

    public ExcelStyleAnnotationCellWriteHandler(Class c, WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        super(headWriteCellStyle, contentWriteCellStyle);
        this.c = c;
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        try {
            Field declaredField = c.getDeclaredField(head.getFieldName());
            ExcelStyle annotation = declaredField.getAnnotation(ExcelStyle.class);
            if (annotation != null) {
                Workbook wb = cell.getSheet().getWorkbook();
                CellStyle cellStyle = wb.createCellStyle();
                Font font = wb.createFont();
                font.setFontName(annotation.fontName());
                font.setFontHeightInPoints(annotation.fontHeightInPoints());
                cellStyle.setFont(font);
                cellStyle.setAlignment(annotation.horizontalAlignment());
                cellStyle.setVerticalAlignment(annotation.verticalAlignment());
                cell.setCellStyle(cellStyle);
            }else {
                super.setContentCellStyle(cell,head,relativeRowIndex);
            }
        } catch (NoSuchFieldException e) {
            log.error("ExcelStyleAnnotationCellWriteHandler error{0}",e);
        }

    }
}
