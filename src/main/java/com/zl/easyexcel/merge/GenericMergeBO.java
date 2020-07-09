package com.zl.easyexcel.merge;

import lombok.Data;

/**
 * @author zhangliang
 * @date 2020/5/25.
 */
@Data
public class GenericMergeBO {
    /**
     * 是否是开始合并的单元格
     */
    private Boolean startMergeCell;
    /**
     * 合并的行数
     */
    private Integer rowspan;
    /**
     * 合并的列数
     */
    private Integer colspan;
}
