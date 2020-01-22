package com.zl.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


@Data
@Slf4j
public class SimpleDataListener<E> extends AbstractDataListener<E>{


    public SimpleDataListener(List<String> headList){
        super(headList,true);
    }

    public SimpleDataListener(Class<E> c,boolean validate){
       super(c,validate);
    }

    @Override
    public void invoke(E data, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = getPropertyNameMap(true,analysisContext);
        if (validate(data,propertyNameMap)) {
            dataList.add(data);
        }
    }
}
