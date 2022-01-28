package com.zl.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.CellData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author tzxx
 */
@Data
@Slf4j
public class MultiHeadersDataListener<E> extends AbstractDataListener<E>{
    private int headRowNumber;

    public MultiHeadersDataListener(){}
    public MultiHeadersDataListener(Class<E> c,int headRowNumber){
        super(c,true);
        this.headRowNumber = headRowNumber;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        Collection<CellData> head = headMap.values();
        excelHeadNames.addAll(head.stream().map(CellData::getStringValue).collect(Collectors.toList()));
        if (headRowNumber ==  1){
            if (excelHeadNames.isEmpty() || !excelHeadNames.containsAll(headList)) {
                throw new ExcelAnalysisException("导入的excel内容有误,请重新上传");
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
