package com.zl.easyexcel.write;

import com.alibaba.excel.metadata.Head;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface HeadConsumer<T extends HeadConsumer.HeadConsumerHelper> {
    void accept(T t);

    @Data
    public static class HeadConsumerHelper{
        private Workbook workbook;
        private Cell cell;
        private Head head;
        private Integer relativeRowIndex;
        private CellStyle headCellStyle;
    }
}