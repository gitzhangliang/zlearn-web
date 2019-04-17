package com.zl.model.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class ExcelUtils {
	private static final String EXPORT_EXCLE_INDEX = "export.excle.index";
	private static final String EXCLE_TITLES = ".titles";
	private static final String EXCLE_FIELDS = ".fields";
	private static final String EXCLE_NAME = ".excleName";
	private static final String SHEET_NAME = ".sheetName";
	private Properties props = new Properties();

	public  ExcelAttr getExcleTitlesAndFields(String index){
		ExcelAttr ea = new ExcelAttr();
		try {
			props.load(ExcelUtils.class.getResourceAsStream("/export.properties"));
			String[] indexProp = getPropertys(EXPORT_EXCLE_INDEX);
			for(String prop : indexProp){
				if(prop.equals(index)){
					String titleKey = getKey(prop, EXCLE_TITLES);
					String fieldKey = getKey(prop, EXCLE_FIELDS);
					String fileNameKey = getKey(prop, EXCLE_NAME);
					String sheetNameKey = getKey(prop, SHEET_NAME);
					String[] titles = getPropertys(titleKey);
					String[] fields = getPropertys(fieldKey);
					ea.setFileName(getProperty(fileNameKey));
					ea.setSheetName(getProperty(sheetNameKey));
					ea.setExcelTitles(titles);
					ea.setExcelFields(fields);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ea;
	}

	public String[] getPropertys(String key) throws UnsupportedEncodingException{
		String prop =  props.getProperty(key);
		if(prop == null){
			return new String[0] ;
		}
		String s = new String(prop.getBytes("ISO-8859-1"), "UTF-8");
		return s.split(",");
	}

	public String getKey(String indexPro,String postfix){
		StringBuilder sb = new StringBuilder();
		sb.append(indexPro).append(postfix);
		return sb.toString();
	}

	public String getProperty(String key) throws UnsupportedEncodingException{
		String prop =  props.getProperty(key);
		if(prop == null){
			return "";
		}
		return new String(prop.getBytes("ISO-8859-1"), "UTF-8");
	}




	//通过对象的属性拿到对应的值
	public static Object getFieldValueByName(String fieldName, Object obj) {
		if("".equals(fieldName)){
			return "";
		}
		String firstLetter = fieldName.substring(0,1).toUpperCase();
		String getter = "get" +firstLetter + fieldName.substring(1);
		try {
			Method method = obj.getClass().getMethod(getter, new Class[]{});
			Object value = method.invoke(obj, new Object[]{});
			if(value == null){
				return "";
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取数据失败");
			return null;
		}
	}

	//将得到的value转为String
	public static String getToStringValue(String fieldName, Object obj) {
		Object objectValue = getFieldValueByName(fieldName, obj);
		if(objectValue instanceof Date){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			objectValue = format.format(objectValue).toString();
		}
		if(objectValue instanceof Double){
			DecimalFormat df=(DecimalFormat)NumberFormat.getInstance();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			objectValue = df.format(objectValue);
		}
		if(objectValue instanceof Integer){
			objectValue = objectValue.toString();
		}
		if(objectValue instanceof String && "0.00".equals(objectValue)){
			return "0.00";
		}
		return String.valueOf(objectValue);
	}

	public static XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontName("微软雅黑");
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
	
	public static XSSFCellStyle getDoubleStyle(XSSFWorkbook wb) {
    	XSSFCellStyle style = wb.createCellStyle();
		XSSFDataFormat format3 = wb.createDataFormat();
		style.setDataFormat(format3.getFormat("#,##0.00"));
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 生成另一个字体
//		XSSFFont font = wb.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		font.setFontName("微软雅黑");
		// 把字体应用到当前的样式
//		style.setFont(font);
		return style;
	}

}
