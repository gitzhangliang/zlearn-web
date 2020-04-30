package com.zl.job.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * CJM 2019/10/29.
 */
@Data
public class PageListResp<T> {

    private List<T> resultList;

    private long rows;
    public PageListResp(){}
    public PageListResp(List<T> resultList, long rows){
        this.resultList = resultList;
        this.rows = rows;
    }

    public PageListResp(IPage<T> page){
        resultList = page.getRecords();
        rows = page.getTotal();
    }
}
