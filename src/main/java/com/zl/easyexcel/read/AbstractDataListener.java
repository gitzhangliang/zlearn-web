package com.zl.easyexcel.read;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadHolder;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.groups.Default;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangliang
 * @date 2019/12/17.
 */
@Data
public abstract class AbstractDataListener<E> extends AnalysisEventListener<E>{
    protected List<E> dataList = new ArrayList<>();
    protected List<ExcelErrorDTO> errorList = new ArrayList<>();
    protected boolean success = true;
    protected ImportExcelResult<E, ExcelErrorDTO> result = new ImportExcelResult<>();
    protected List<String> headList = new ArrayList<>();
    protected boolean validate;


    protected Set<String> excelHeadNames = new HashSet<>();

    AbstractDataListener(){}
    AbstractDataListener(List<String> headList,boolean validate){
        this.headList = headList;
        this.validate = validate;
    }

    AbstractDataListener(Class<E> c,boolean validate){
        Field[] declaredFields = c.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                String[] value = annotation.value();
                headList.addAll(Arrays.asList(value));
            }
        }
        this.validate = validate;
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        Collection<CellData> head = headMap.values();
        excelHeadNames.addAll(head.stream().map(CellData::getStringValue).collect(Collectors.toList()));
        if (validate && !headList.containsAll(excelHeadNames)) {
            throw new ExcelAnalysisException("导入的excel表头有误,请重新下载模板");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        result.setData(getDataList());
        result.setError(getErrorList());
        result.setSuccess(getErrorList().isEmpty());
        if (getErrorList().isEmpty() && getDataList().isEmpty()){
            result.setSuccess(false);
            ExcelErrorDTO errorDTO = new ExcelErrorDTO();
            errorDTO.setErrMsg("excel无数据");
            errorList.add(errorDTO);
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context){
        if(exception instanceof ExcelDataConvertException){
            ExcelDataConvertException e = (ExcelDataConvertException)exception;
            handleExcelDataConvertException(e);
        }else {
            throw new ExcelAnalysisException(exception.getMessage());
        }
    }

    /**使用javax validate校验
     * @param e 需要校验的对象
     * @param propertyNameMap 对象excel数据
     * @return true
     */
     boolean validate(E e, Map<String, ExcelCellBo> propertyNameMap) {
        boolean validateResult = true;
        Set<ConstraintViolation<E>> validateSet = Validation.buildDefaultValidatorFactory().getValidator().validate(e, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            validateResult = false;
            ExcelErrorDTO errorDTO;
            List<ExcelErrorDTO> list  = new ArrayList<>();
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
                list.add(errorDTO);
            }
            Collections.sort(list);
            errorList.addAll(list);
        }
        return validateResult;
    }

    /**处理ExcelDataConvertException
     * @param e 字段转换异常
     */
     private void handleExcelDataConvertException(ExcelDataConvertException e){
        ExcelErrorDTO errorDTO = new ExcelErrorDTO();
        errorDTO.setHeadName(e.getExcelContentProperty().getHead().getHeadNameList().get(0));
        errorDTO.setValue(e.getCellData().getStringValue());
        errorDTO.setColumnIndex(e.getColumnIndex()+1);
        errorDTO.setRowIndex(e.getRowIndex()+1);
        errorDTO.setErrMsg("第"+errorDTO.getRowIndex()+"行第"+errorDTO.getColumnIndex()+"列,"+errorDTO.getHeadName()+"值格式错误");
        errorList.add(errorDTO);
    }

    /**获取excel PropertyNameMap
     * @param isSingleHeader 是否单表头
     * @param analysisContext AnalysisContext
     * @return Map
     */
     Map<String, ExcelCellBo> getPropertyNameMap(boolean isSingleHeader, AnalysisContext analysisContext){
        Map<String, ExcelCellBo> propertyNameMap = new HashMap<>(16);
        ReadRowHolder readRowHolder = analysisContext.readRowHolder();
        Integer rowIndex = readRowHolder.getRowIndex();
        ReadHolder readHolder = analysisContext.currentReadHolder();
        ExcelReadHeadProperty excelReadHeadProperty = readHolder.excelReadHeadProperty();
        Map<String, ExcelContentProperty> fieldNameContentPropertyMap = excelReadHeadProperty.getFieldNameContentPropertyMap();
        Collection<ExcelContentProperty> values = fieldNameContentPropertyMap.values();
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
        return propertyNameMap;
    }
}
