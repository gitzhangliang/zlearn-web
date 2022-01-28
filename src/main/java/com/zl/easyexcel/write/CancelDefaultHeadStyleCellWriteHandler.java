package com.zl.easyexcel.write;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author zhangliang
 * @date 2020/11/11.
 */
public class CancelDefaultHeadStyleCellWriteHandler extends AbstractCellStyleStrategy {
    private final HeadConsumer<HeadConsumer.HeadConsumerHelper> headStyleConsumer;
    public CancelDefaultHeadStyleCellWriteHandler(HeadConsumer<HeadConsumer.HeadConsumerHelper> headStyleConsumer) {
        this.headStyleConsumer = headStyleConsumer;
    }
    @Override
    protected void initCellStyle(Workbook workbook) {
        //NO-OP
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        if (headStyleConsumer != null) {
            HeadConsumer.HeadConsumerHelper helper = new HeadConsumer.HeadConsumerHelper();
            helper.setHeadCellStyle(cellStyle);
            helper.setHead(head);
            helper.setCell(cell);
            helper.setRelativeRowIndex(relativeRowIndex);
            helper.setWorkbook(workbook);
            headStyleConsumer.accept(helper);
        }
        cell.setCellStyle(cellStyle);
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        //NO-OP
    }

}
