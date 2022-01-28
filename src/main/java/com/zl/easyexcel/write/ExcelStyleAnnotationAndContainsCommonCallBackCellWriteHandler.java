package com.zl.easyexcel.write;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.zl.easyexcel.annotation.ExcelStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangliang
 * @date 2019/12/19.
 */
@Slf4j
public class ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler<T> extends HorizontalCellStyleStrategy {

    private final Class<?> c;
    private final List<T> dataList;
    private final Consumer<CommonCallBackData<T>> fontConsumer;
    private final Consumer<CommonCallBackData<T>> cellStyleConsumer;
    private final Consumer<CommonCallBackData<T>> richTextConsumer;
    public ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler(Class<?> c, WriteCellStyle headWriteCellStyle,
                                                                         WriteCellStyle contentWriteCellStyle,
                                                                         List<T> dataList) {
        super(headWriteCellStyle, contentWriteCellStyle);
        this.c = c;
        this.dataList = dataList;
        this.fontConsumer = null;
        this.cellStyleConsumer = null;
        this.richTextConsumer = null;
    }
    public ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler(Class<?> c, WriteCellStyle headWriteCellStyle,
                                                                         WriteCellStyle contentWriteCellStyle,
                                                                         List<T> dataList,
                                                                         Consumer<CommonCallBackData<T>> fontConsumer,
                                                                         Consumer<CommonCallBackData<T>> cellStyleConsumer,
                                                                         Consumer<CommonCallBackData<T>> richTextConsumer) {
        super(headWriteCellStyle, contentWriteCellStyle);
        this.c = c;
        this.fontConsumer = fontConsumer;
        this.cellStyleConsumer = cellStyleConsumer;
        this.richTextConsumer = richTextConsumer;
        this.dataList = dataList;
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        try {
            Field declaredField = c.getDeclaredField(head.getFieldName());
            ExcelStyle annotation = declaredField.getAnnotation(ExcelStyle.class);
            if (annotation != null) {
                Workbook wb = cell.getSheet().getWorkbook();
                CellStyle cellStyle = wb.createCellStyle();
                cell.setCellStyle(cellStyle);
                Font font = wb.createFont();
                CommonCallBackData<T> callBackData = create(font,cellStyle,relativeRowIndex,cell,wb,annotation,declaredField);
                font.setFontName(annotation.fontName());
                font.setFontHeightInPoints(annotation.fontHeightInPoints());
                if (fontConsumer != null) {
                    fontConsumer.accept(callBackData);
                }
                cellStyle.setFont(font);
                cellStyle.setAlignment(annotation.horizontalAlignment());
                cellStyle.setVerticalAlignment(annotation.verticalAlignment());
                cellStyle.setWrapText(annotation.wrapText());
                if (cellStyleConsumer != null) {
                    cellStyleConsumer.accept(callBackData);
                }
                boolean richText = annotation.richText();
                if (richText && richTextConsumer != null) {
                    richTextConsumer.accept(callBackData);
                }
            }else {
                super.setContentCellStyle(cell,head,relativeRowIndex);
            }
        } catch (NoSuchFieldException e) {
            log.error("ExcelStyleAnnotationCellWriteHandler error{0}",e);
        }

    }

    private CommonCallBackData<T> create(Font font, CellStyle cellStyle, Integer relativeRowIndex,Cell cell,Workbook wb,ExcelStyle annotation, Field field){
        CommonCallBackData<T> callBackData = new CommonCallBackData<>();
        callBackData.setFont(font);
        callBackData.setCellStyle(cellStyle);
        callBackData.setRelativeRowIndex(relativeRowIndex);
        callBackData.setDataList(this.dataList);
        callBackData.setCell(cell);
        callBackData.setWorkbook(wb);
        callBackData.setAnnotation(annotation);
        callBackData.setField(field);
        return callBackData;
    }

    @Override
    public String uniqueValue() {
        return this.getClass().getSimpleName();
    }

    public static void richTextConsumer(CommonCallBackData<?> callBackData,String extraStr){
        Workbook workbook = callBackData.getWorkbook();
        ExcelStyle annotation = callBackData.getAnnotation();
        Cell cell = callBackData.getCell();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setFontName(annotation.fontName());
        font.setFontHeightInPoints(annotation.fontHeightInPoints());
        Font defaultFont = callBackData.getFont();
        String value = extraStr + cell.getStringCellValue();
        RichTextString richTextString = new XSSFRichTextString(value);
        richTextString.applyFont(0,extraStr.length(),font);
        richTextString.applyFont(extraStr.length(),value.length(),defaultFont);
        cell.setCellValue(richTextString);
    }
}
