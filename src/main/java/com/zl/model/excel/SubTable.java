package com.zl.model.excel;

import java.util.ArrayList;
import java.util.List;

public class SubTable{
	private List<Row> rows = new ArrayList<>();
	
	public void add(Row row) {
		rows.add(row);
	}

	public int rowSize() {
		// TODO Auto-generated method stub
		return rows.size();
	}
	
	public List<Cell> getCellsByRowIndex(int rowIndex) {
		List<Cell> cells = new ArrayList<>();
		int rowStart = 0;
		for (Row row : rows) {
			if(rowStart == rowIndex) {
				for (Cell cell : row) {
					cells.add(cell);
				}
				break;
			}
			rowStart += row.maxRowSpan();
		}
		return cells;
	}

}


