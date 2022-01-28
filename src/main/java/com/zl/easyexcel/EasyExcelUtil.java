package com.zl.easyexcel;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.zl.easyexcel.write.CommonCallBackData;
import com.zl.easyexcel.write.ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler;


import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangliang
 * @date 2019/12/10.
 */
public class EasyExcelUtil {
    private EasyExcelUtil(){}

    public static <T> ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler<T> getWholeCellWriteHandler(Class<?> c, List<T> dataList,
                                                                                                                Consumer<CommonCallBackData<T>> fontConsumer,
                                                                                                                Consumer<CommonCallBackData<T>> cellStyleConsumer,
                                                                                                                Consumer<CommonCallBackData<T>> richTextConsumer) {
        return new ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler<>(c,getHeadWriteCellStyle(), getContentWriteCellStyle(),dataList,fontConsumer,cellStyleConsumer,richTextConsumer);
    }

    public static <T> ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler<T> getDefaultCellWriteHandler(Class<?> c,List<T> dataList) {
        return new ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler<>(c,getHeadWriteCellStyle(), getContentWriteCellStyle(),dataList,null,null,null);
    }




    private static WriteCellStyle getHeadWriteCellStyle(){
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)14);
        headWriteFont.setFontName("微软雅黑");
        headWriteCellStyle.setWriteFont(headWriteFont);
        return headWriteCellStyle;
    }
    private static WriteCellStyle getContentWriteCellStyle(){
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("微软雅黑");
        contentWriteFont.setFontHeightInPoints((short)13);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return contentWriteCellStyle;
    }
}
