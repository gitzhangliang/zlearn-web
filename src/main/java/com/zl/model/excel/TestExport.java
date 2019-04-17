package com.zl.model.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestExport {
	public static void main(String[] args) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet xsSheet = wb.createSheet("sheet0");
		Sheet doc = new Sheet();
		createLeftTable(doc);
		createRightTable(doc);
		createDateTable(doc);
		
		System.out.println(doc);
		SheetOutput out = new SheetOutput();
		//out.outputHTML(doc);
		//TODO 需要修改
		List<Sheet> sheetList = new ArrayList<Sheet>();
		sheetList.add(doc);
		out.outputExcel(sheetList,wb);
		export("d:/1.xlsx",wb);
	}
	public static void export(String filePath,Workbook workbook){
		OutputStream stream = null;
		try {
			stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			workbook = new XSSFWorkbook();
			workbook.write(stream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(workbook != null){
					workbook.close();
				}
				if(stream != null){
					stream.flush();
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	private static void createDateTable(Sheet doc) {
		// TODO Auto-generated method stub
		SubTable table = new SubTable();
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1");
			row.add(cell);
			table.add(row);
		}
		doc.add(table);
		
	}

	private static void createRightTable(Sheet doc) {
		SubTable table = new SubTable();
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("合计");
			cell.setColSpan(2);
			row.add(cell);
			table.add(row);
		}
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("数量");
			row.add(cell);
			cell = new Cell();
			cell.setValue("预结金额");
			row.add(cell);
			table.add(row);
		}
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("30");
			row.add(cell);
			cell = new Cell();
			cell.setValue("400");
			row.add(cell);
			table.add(row);
		}
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("60");
			row.add(cell);
			cell = new Cell();
			cell.setValue("1400");
			row.add(cell);
			table.add(row);
		}
		
		doc.add(table);
	}

	private static void createLeftTable(Sheet doc) {

		SubTable table = new SubTable();
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("合同名称");
			cell.setRowSpan(2);
			row.add(cell);
			table.add(row);
		}
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("合同1");
			row.add(cell);
			table.add(row);
		}
		{
			Row row = new Row();
			Cell cell = new Cell();
			cell.setValue("合同2");
			row.add(cell);
			table.add(row);
		}
		doc.add(table);

	}

}
