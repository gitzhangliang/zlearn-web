package com.zl.model.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
/**
 * @author zl
 */
@Getter
@Setter
public class Cell {
	
	private Integer colSpan = 1;
	
	private Integer rowSpan = 1;

	private String value;
	
	private Double valueDouble;
	
	private Integer type;
	
	private XSSFCellStyle style;
	
	private Integer columnWidth;
}
