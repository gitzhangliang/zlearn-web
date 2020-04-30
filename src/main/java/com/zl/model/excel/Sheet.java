package com.zl.model.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zl
 */
public class Sheet implements Iterable<SubTable>{
	private List<SubTable> tables = new ArrayList<SubTable>();
	private String sheetName;
	public void add(SubTable table) {
		tables.add(table);
	}

	public int getMaxRowSize() {
		// TODO Auto-generated method stub
		int maxRowSize = tables.get(0).rowSize();
		for (SubTable subTable : tables) {
			if (maxRowSize < subTable.rowSize()) {
				maxRowSize = subTable.rowSize();
			}
		}
		return maxRowSize;
	}

	@Override
	public Iterator<SubTable> iterator() {
		// TODO Auto-generated method stub
		return tables.iterator();
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public static void main(String[] args) {
		Sheet sheet = new Sheet();
		sheet.tables.add(new SubTable());
		sheet.tables.add(new SubTable());
		for (SubTable subTable : sheet) {
			System.out.println(subTable.toString());
		}
	}
}
