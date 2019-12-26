package com.zl.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**当onException触发时，会直接跳过该行其它未检测的cell数据，直接开始校验下一行数据，
 * 且不会触发validate数据校验方法(onException方法中不抛出异常的情况下)[easy excel文档原文：在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。]。
 * 如果要解决此问题，则使用string接收所有数据（避免转换异常ExcelDataConvertException），通过validate校验数据（该方案不会触发onException）
 * @author zhangliang
 * @date 2019/11/15.
 */
@Data
@Slf4j
public class SimpleDataListener<E> extends AbstractDataListener<E>{


    public SimpleDataListener(List<String> headList){
        super(headList,true);
    }

    public SimpleDataListener(Class<E> c){
       super(c,true);
    }

    @Override
    public void invoke(E data, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = getPropertyNameMap(true,analysisContext);
        if (validate(data,propertyNameMap)) {
            dataList.add(data);
        }
    }
}
