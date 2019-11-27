package com.zl.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**当onException触发时，会直接跳过该行其它未检测的cell数据，直接开始校验下一行数据，且不会触发validate数据校验方法(onException方法中不抛出异常的情况下)。
 * 如果要解决此问题，则使用string接收所有数据，通过validate校验数据（该方案不会触发onException）
 * @author zhangliang
 * @date 2019/11/15.
 */
@Data
@Slf4j
public class SimpleDataListener<E> extends AnalysisEventListener<E> {

    private List<E> dataList = new ArrayList<>();
    private List<ExcelErrorDTO> errorList = new ArrayList<>();
    private boolean success = true;
    ImportExcelResult<E, ExcelErrorDTO> result = new ImportExcelResult<>();
    private List<String> headList;
    private boolean excelHead = true;

    public SimpleDataListener(List<String> headList){
        this.headList = headList;
    }

    public SimpleDataListener(Class<E> c){
        headList = new ArrayList<>();
        Field[] declaredFields = c.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String[] value = annotation.value();
                headList.addAll(Arrays.asList(value));
            }
        }
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        Collection<CellData> head = headMap.values();
        List<String> headNames = head.stream().map(CellData::getStringValue).collect(Collectors.toList());
        headList.removeAll(headNames);
        excelHead = headList.isEmpty();
        if (!excelHead) {
            throw new RuntimeException("导入excel表头有误,请上传填写后的excel下载模板");
        }
    }

    @Override
    public void invoke(E data, AnalysisContext analysisContext) {
        Map<String, ExcelCellBo> propertyNameMap = new HashMap<>(16);
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        ReadHolder readHolder = analysisContext.currentReadHolder();
        ExcelReadHeadProperty excelReadHeadProperty = readHolder.excelReadHeadProperty();
        Map<Integer, ExcelContentProperty> contentPropertyMap = excelReadHeadProperty.getContentPropertyMap();
        Collection<ExcelContentProperty> values = contentPropertyMap.values();
        ExcelCellBo bo;
        for (ExcelContentProperty contentProperty : values) {
            bo = new ExcelCellBo();
            bo.setRowIndex(rowIndex);
            bo.setColumnIndex(contentProperty.getHead().getColumnIndex());
            bo.setFieldName(contentProperty.getHead().getFieldName());
            bo.setHeadName(contentProperty.getHead().getHeadNameList().get(0));
            bo.setField(contentProperty.getField());
            propertyNameMap.put(contentProperty.getHead().getFieldName(),bo);
        }
        if (validate(data,propertyNameMap)) {
            dataList.add(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        result.setData(getDataList());
        result.setError(getErrorList());
        result.setSuccess(getErrorList().isEmpty());
    }
    @Override
    public void onException(Exception exception, AnalysisContext context){
        if(exception instanceof ExcelDataConvertException){
            ExcelDataConvertException e = (ExcelDataConvertException)exception;
            ExcelErrorDTO errorDTO = new ExcelErrorDTO();
            errorDTO.setHeadName(e.getExcelContentProperty().getHead().getHeadNameList().get(0));
            errorDTO.setValue(e.getCellData().getStringValue());
            errorDTO.setColumnIndex(e.getColumnIndex()+1);
            errorDTO.setRowIndex(e.getRowIndex()+1);
            errorDTO.setErrMsg("第"+errorDTO.getRowIndex()+"行第"+errorDTO.getColumnIndex()+"列,"+errorDTO.getHeadName()+"值格式错误");
            errorList.add(errorDTO);
        }else {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private boolean validate(E e, Map<String, ExcelCellBo> propertyNameMap) {
        boolean validateResult = true;
        Set<ConstraintViolation<E>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(e, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            validateResult = false;
            ExcelErrorDTO errorDTO;
            for (ConstraintViolation<E> constraint : validateSet) {
                Path propertyPath = constraint.getPropertyPath();
                String propertyName = propertyPath.toString();
                ExcelCellBo bo = propertyNameMap.get(propertyName);
                errorDTO = new ExcelErrorDTO();
                errorDTO.setHeadName(bo.getHeadName());
                Object invalidValue = constraint.getInvalidValue();
                if (invalidValue != null) {
                    errorDTO.setValue(invalidValue.toString());
                }else {
                    errorDTO.setValue(null);
                }
                errorDTO.setColumnIndex(bo.getColumnIndex()+1);
                errorDTO.setRowIndex(bo.getRowIndex()+1);
                errorDTO.setErrMsg("第"+errorDTO.getRowIndex()+"行第"+errorDTO.getColumnIndex()+"列,"+constraint.getMessage());
                errorList.add(errorDTO);
            }
        }
        return validateResult;
    }

    @Data
    private static class ExcelCellBo{
        private Field field;
        private String fieldName;
        private String headName;
        private Integer columnIndex;
        private Integer rowIndex;
    }
}
