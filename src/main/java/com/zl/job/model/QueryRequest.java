package com.zl.job.model;

import lombok.Data;

@Data
public class QueryRequest<T>{

    private int pageSize = 10;
    private int pageNum = 1;

    private String sortField;
    private String sortOrder;

    private T param;

    private String keyword;
}
