package com.zl.easyexcel.read;

import lombok.Data;

/**
 * @author zhangliang
 * @date 2019/11/15.
 */
@Data
public class ExcelErrorDTO implements Comparable<ExcelErrorDTO>{
    private String value;
    private Integer columnIndex;
    private Integer rowIndex;
    private String headName;
    private String errMsg;

    @Override
    public int compareTo(ExcelErrorDTO o) {
        if (this.getRowIndex() == null){
            return 1;
        }
        if (o.getRowIndex() == null){
            return -1;
        }
        if (this.getRowIndex() < o.getRowIndex()) {
            return -1;
        }else if (this.getRowIndex() > o.getRowIndex()) {
            return 1;
        }else {
            if (this.getColumnIndex() == null){
                return 1;
            }
            if (o.getColumnIndex() == null){
                return -1;
            }
            return this.getColumnIndex().compareTo(o.getColumnIndex());
        }
    }
}
