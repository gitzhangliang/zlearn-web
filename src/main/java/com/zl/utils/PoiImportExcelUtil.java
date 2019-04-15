package com.zl.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class PoiImportExcelUtil { 
	
	private static final PoiImportExcelUtil util = new PoiImportExcelUtil();
	
	private PoiImportExcelUtil(){}
	
	public static PoiImportExcelUtil getUtil(){
		return util;
	}
    
    /**  
    * 读取excel单元格值  
    * @param sheet   
    * @param row   
    * @param cell  
    * @param rownum 行号
    */  
    public String readCellValue(Sheet sheet, Row row, Cell cell, int rownum) {      
    	if(cell==null){
    		return "";
    	}
        boolean isMerge = isMergedRegion(sheet, rownum, cell.getColumnIndex());  
        //判断是否具有合并单元格  
        if(isMerge) {  
        	return getMergedRegionValue(sheet, row.getRowNum(), cell.getColumnIndex());  
        }else {  
            return getCellValue(cell);  
        } 
    } 
      
    /**   
    * 获取合并单元格的值   
    * @param sheet   
    * @param row   
    * @param column   
    * @return   
    */    
    public String getMergedRegionValue(Sheet sheet ,int row , int column){    
        
        int sheetMergeCount = sheet.getNumMergedRegions();    
        
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);    
            int firstColumn = ca.getFirstColumn();    
            int lastColumn = ca.getLastColumn();    
            int firstRow = ca.getFirstRow();    
            int lastRow = ca.getLastRow();    
            
            if(row >= firstRow && row <= lastRow){    
                    
                if(column >= firstColumn && column <= lastColumn){    
                    Row fRow = sheet.getRow(firstRow);    
                    Cell fCell = fRow.getCell(firstColumn);    
                    return getCellValue(fCell) ;    
                }    
            }    
        }    
            
        return null ;    
    }     
      
    /**  
    * 判断指定的单元格是否是合并单元格  
    * @param sheet   
    * @param row 行下标  
    * @param column 列下标  
    * @return  
    */  
    private boolean isMergedRegion(Sheet sheet,int row ,int column) {  
      
      int sheetMergeCount = sheet.getNumMergedRegions();  
      for (int i = 0; i < sheetMergeCount; i++) {  
        
        CellRangeAddress range = sheet.getMergedRegion(i);  
        int firstColumn = range.getFirstColumn();  
        int lastColumn = range.getLastColumn();  
        int firstRow = range.getFirstRow();  
        int lastRow = range.getLastRow();  
        if(row >= firstRow && row <= lastRow){  
            if(column >= firstColumn && column <= lastColumn){  
                return true;  
            }  
        }  
      }  
      return false;  
    }  
        
      
    /**   
    * 获取单元格的值   
    * @param cell   
    * @return   
    */    
    public String getCellValue(Cell cell){    
            
        if(cell == null) return "";    
            
        if(cell.getCellTypeEnum() == CellType.STRING){                    
            return cell.getStringCellValue();                    
        }else if(cell.getCellTypeEnum() == CellType.BOOLEAN){                    
            return String.valueOf(cell.getBooleanCellValue());                   
        }else if(cell.getCellTypeEnum() == CellType.FORMULA){                    
            return cell.getCellFormula() ;                    
        }else if(cell.getCellTypeEnum() == CellType.NUMERIC){
            DecimalFormat df = new DecimalFormat();
            String value =df.format(cell.getNumericCellValue());
			if (value.indexOf(",") >= 0) {
				value = value.replace(",", "");
			}	
            return value;    
        }    
        return "";    
    }

    public Workbook getWorkbook(InputStream is, String fileName) {
        Workbook wb = null;
        try {
            if (isExcel2007(fileName)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return wb;
    }


    /**
     * 描述：是否是2003的excel，返回true是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 描述：是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
} 