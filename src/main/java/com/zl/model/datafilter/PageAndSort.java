package com.zl.model.datafilter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzxx
 */
@Getter
@Setter
public class PageAndSort {

	private Sort sort;

	private Pageable pageable;

	public PageAndSort() {
	}

	public static Pageable getPageable(Filter filter){
		Pageable pageable = PageRequest.of(filter.getPage()-1, filter.getSize());
		return pageable;
	}

	public static Pageable getPageableAndSort(Filter filter){
		if(StringUtils.isBlank(filter.getOrderField()) || StringUtils.isBlank(filter.getDir())){
			filter.setDir(Sort.Direction.DESC.toString());
		}
		Sort sort = new Sort(Sort.Direction.fromString(filter.getDir()), filter.getOrderField());
		Pageable pageable = PageRequest.of(filter.getPage()-1, filter.getSize(), sort);
		return pageable;
	}
	
	public static <T> List<T> pageToList(Page<T> page){
		List<T> list = new ArrayList<>();
		for (T t : page) {
			list.add(t);
		}
		return list;
	}
}

