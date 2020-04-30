package com.zl.model.datafilter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zl
 */
@Getter
@Setter
public class PagedResult<T>{

	private List<T> list;
	private Integer count;

	public PagedResult() {
	}

	public PagedResult(List<T> list, Integer count){
		this.list =  list;
		this.count = count;
	}
	
	public List<T> list() {
		return list;
	}
	
	public Integer count() {
		return count;
	}
}