package com.zl.job.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.stream.IntStream;

/**
 * @author zl
 * @date 2019/11/1.
 */
public class PageAndSort {
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    private PageAndSort(){}
    public static <T> Page<T> constructPage(QueryRequest queryRequest, String defaultSortField, String defaultOrder){
        Page<T> page = new Page<>(queryRequest.getPageNum(),queryRequest.getPageSize());
        if(StringUtils.isNotBlank(queryRequest.getSortField()) && StringUtils.isNotBlank(queryRequest.getSortOrder())){
            if(ASC.equals(queryRequest.getSortOrder())){
                page.addOrder(OrderItem.asc(queryRequest.getSortField()));
            }else if(DESC.equals(queryRequest.getSortOrder())){
                page.addOrder(OrderItem.desc(queryRequest.getSortField()));
            }
        }
        if(StringUtils.isNotBlank(defaultSortField) && StringUtils.isNotBlank(defaultOrder)){
            if(ASC.equals(defaultOrder)){
                page.addOrder(OrderItem.asc(defaultSortField));
            }else if(DESC.equals(defaultOrder)){
                page.addOrder(OrderItem.desc(defaultSortField));
            }
        }
        return page;
    }


    /**如果类的字段和数据库字段是驼峰转下划线，可以使用此方法
     * eg: Dept parentId 数据库parent_id,则调用方式为（Dept::getParentId,defaultOrder,queryRequest）
     * 和调用方式为（queryRequest，"parent_id",defaultOrder）是同一个效果
     */
    public static <T> Page<T> constructPage(SFunction<T, ?> func, String defaultOrder, QueryRequest queryRequest){
        SerializedLambda lambda = SerializedLambda.resolve(func);
        String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        return constructPage(queryRequest,camelToUnderscore(fieldName),defaultOrder);
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)){
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0){
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1){
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            } else{
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }
}
