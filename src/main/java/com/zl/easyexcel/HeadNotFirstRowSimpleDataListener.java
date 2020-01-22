package com.zl.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2019/12/26.
 */
@Data
public class HeadNotFirstRowSimpleDataListener<E> extends SimpleDataListener<E> {
    private int skipRowCount;

    public HeadNotFirstRowSimpleDataListener(List<String> headList) {
        super(headList);
    }

    public HeadNotFirstRowSimpleDataListener(Class<E> c, boolean validate,int skipRowCount) {
        super(c, validate);
        this.skipRowCount = skipRowCount;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        if (skipRowCount == 0) {
            super.invokeHead(headMap,context);
        }else {

        }
    }
}
