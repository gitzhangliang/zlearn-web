package com.zl.model.datafilter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zl
 */
@Getter
@Setter
public class Filter {

	private int page;

	private int size;

	private String keyword;

	private String dir;

	private String orderField;
}
