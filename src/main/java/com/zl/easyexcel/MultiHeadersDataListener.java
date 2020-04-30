package com.zl.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author zl
 */
@Data
@Slf4j
public class MultiHeadersDataListener<E> extends AbstractDataListener<E>{

    private int headRowNumber;


    public MultiHeadersDataListener(Class<E> c,boolean validate,int headRowNumber){
        super(c,validate);
        this.headRowNumber  = headRowNumber;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        Collection<CellData> head = headMap.values();
        excelHeadNames.addAll(head.stream().map(CellData::getStringValue).collect(Collectors.toList()));
        if (headRowNumber ==  1){
            if (!headList.containsAll(excelHeadNames)) {
                throw new RuntimeException("导入excel表头有误,请上传填写后的excel下载模板");
            }
        }else {
            headRowNumber--;
        }
    }

    @Override
    public void invoke(E data, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = getPropertyNameMap(false,analysisContext);
        if (validate(data,propertyNameMap)) {
            dataList.add(data);
        }
    }
}
