package com.zl.model.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tzxx
 */
public class Row implements Iterable<Cell>{
	
	private List<Cell> cells = new ArrayList<>();

	public void add(Cell cell) {
		cells.add(cell);
	}

	@Override
	public Iterator<Cell> iterator() {
		// TODO Auto-generated method stub
		return cells.iterator();
	}

	public int maxRowSpan() {
		// TODO Auto-generated method stub
		int rowSpan = cells.get(0).getRowSpan();
		for (Cell cell : cells) {
			if(rowSpan < cell.getRowSpan()) {
				rowSpan = cell.getRowSpan();
			}
		}
		return rowSpan;
	}
}
