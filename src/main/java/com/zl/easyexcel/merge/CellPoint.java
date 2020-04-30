package com.zl.easyexcel.merge;

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

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}