package com.zl.easyexcel.write;

import com.zl.easyexcel.annotation.ExcelStyle;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.List;

/**ExcelStyleAnnotationAndContainsCommonCallBackCellWriteHandler 对应回调的封装实体
 * @author zhangliang
 * @date 2020/10/16.
 */

@Data
public class CommonCallBackData<T> {
    private Font font;
    private CellStyle cellStyle;
    private List<T> dataList;
    private Integer relativeRowIndex;
    private Cell cell;
    private Workbook workbook;
    private Field field;
    private ExcelStyle annotation;
}
