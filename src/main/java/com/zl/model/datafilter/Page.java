package com.zl.model.datafilter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
	private int page;
	private int size;
	public Page() {
		// TODO Auto-generated constructor stub
	}
	public Page(int page, int size) {
		this.page = page;
		this.size = size;
	}
}