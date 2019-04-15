package com.zl.utils;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;

public class PoiExportExcelUtil {
	private static PoiExportExcelUtil util = new PoiExportExcelUtil();
	private CellStyle cellStyle = null;
	private PoiExportExcelUtil() {
		// TODO Auto-generated constructor stub
	}
	public static PoiExportExcelUtil getUtil() {
		return util;
	}
	
	/**
	 * @param wb
	 * @return
	 */
	public PoiExportExcelUtil commonSetting(Workbook wb) {
		cellStyle = wb.createCellStyle();
		cellStyle.setLocked(true);
		//设置自动换行
		cellStyle.setWrapText(true);
		return this;
	}
	/**
	 * 垂直居中
	 */
	public PoiExportExcelUtil setVerticalAlignment() {
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		return this;
	}
	/**
	 * 水平居中
	 */
	public PoiExportExcelUtil setAlignment() {
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return this;
	}

	/**设置字体
	 * @param wb
	 * @return
	 */
	public PoiExportExcelUtil setFont(Workbook wb) {
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		cellStyle.setFont(font);
		return this;
	}

	/**设置背景色
	 * @param color
	 * @return
	 */
	public PoiExportExcelUtil setBackgroundColor(String color) {		
		if ("red".equals(color)) {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.RED.getIndex());
		}else {
			cellStyle.setFillForegroundColor(HSSFColorPredefined.WHITE.getIndex());
		}
		//填充模式
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return this;
	}

	/**设置边框
	 * @return
	 */
	public PoiExportExcelUtil setBorder() {
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		return this;
	}

	public PoiExportExcelUtil setText(Workbook wb) {
		DataFormat dataFormat = wb.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("@"));
		return this;
	}

	public PoiExportExcelUtil setDate(Workbook wb) {
		DataFormat dataFormat = wb.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("yyyy年mm月dd日"));
		return this;
	}
	public PoiExportExcelUtil onlySetTextAndWrap(Workbook wb) {
		cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		DataFormat dataFormat = wb.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("@"));
		return this;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setcellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
	/**默认样式（垂直居中+自动换行）
	 * @param wb
	 * @return
	 */
	public CellStyle verticalAlignment(Workbook wb) {
		return PoiExportExcelUtil.getUtil().commonSetting(wb).setVerticalAlignment().getCellStyle();
	}
	/**默认样式（垂直居中+水平居中+自动换行）
	 * @param wb
	 * @return
	 */
	public CellStyle alignment(Workbook wb) {
		return PoiExportExcelUtil.getUtil().commonSetting(wb).setAlignment().setVerticalAlignment().getCellStyle();
	}
	/**默认样式（水平居中+自动换行）
	 * @param wb
	 * @return
	 */
	public CellStyle levelAlignment(Workbook wb) {
		return PoiExportExcelUtil.getUtil().commonSetting(wb).setAlignment().getCellStyle();
	}
}
