package com.zl.easyexcel.merge;

import lombok.Data;

/**
 * @author tzxx
 */
@Data
public class CellPoint {
    /**
     * 开始单元格x坐标
     * start cell x point
     */
    private int startX;
    /**
     * 结束单元格x坐标
     * end cell x point
     */
    private int endX;
    /**
     * 开始单元格y坐标
     * start cell y point
     */
    private int startY;
    /**
     * 结束单元格y坐标
     * end cell y point
     */
    private int endY;
    /**
     * 单元格内容，文本
     * cell content, text
     */
    private String text;
}