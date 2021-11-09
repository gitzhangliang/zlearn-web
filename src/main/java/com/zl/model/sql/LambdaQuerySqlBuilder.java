package com.zl.model.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public class LambdaQuerySqlBuilder extends AbstractLambdaSqlBuilder<LambdaQuerySqlBuilder> {


    private final List<SqlFunction<?,?>> selected = new ArrayList<>();

    @SafeVarargs
    public final <T,R>  LambdaQuerySqlBuilder select(SqlFunction<T, R> ...columns){
        selected.addAll(Arrays.asList(columns));
        return this;
    }

    public <T,R,K,V>LambdaQuerySqlBuilder selectAlias(SqlFunction<T,R> column,SqlFunction<K,V> alias){
        selected.add(column);
        String className = lambdaClassName(column);
        String property = lambdaProperty(column);
        String propertyAlias = lambdaProperty(alias);
        propertyAliasMap.put(className+"_"+property,propertyAlias);
        return this;
    }

    public LambdaQuerySqlBuilder from(Class<?> table){
        return from(table,null);
    }
    
    public LambdaQuerySqlBuilder from(Class<?> table,String alias){
        return from(null,alias,table);
    }

    public LambdaQuerySqlBuilder from(String table,String alias,Class<?> tableClass){
        analysisTable(tableClass,alias,table);
        sqlBd.append(" from ");
        sqlBd.append(tableMap.get(tableClass));
        sqlBd.append(" ");
        sqlBd.append(tableAliasMap.get(tableClass));
        return this;
    }



    @SafeVarargs
    public final <T, R> LambdaQuerySqlBuilder orderByAsc(SqlFunction<T, R> ...columns) {
        for (SqlFunction<T, R> column : columns) {
            orderByColumns.add(new Sort<>(column,ASC));
        }
        return this;
    }

    @SafeVarargs
    public final <T, R> LambdaQuerySqlBuilder orderByDesc(SqlFunction<T, R> ...columns) {
        for (SqlFunction<T, R> column : columns) {
            orderByColumns.add(new Sort<>(column,DESC));
        }
        return this;
    }

    @SafeVarargs
    public final <T, R> LambdaQuerySqlBuilder groupBy(SqlFunction<T, R>... columns) {
        groupByColumns.addAll(Arrays.asList(columns));
        return this;
    }


    @Override
    public String sql() {
        StringBuilder select = getSelect();
        appendOrderBy();
        appendGroupBy();
        return select.append(sqlBd).toString()
                .replace(" or ()","")
                .replace(" and ()","");
    }

    private StringBuilder getSelect(){
        StringBuilder select = new StringBuilder("select ");
        String humpToLine;
        String alias;
        String property;
        String className;
        int size = selected.size();
        for (SqlFunction<?, ?> column : selected) {
            className = lambdaClassName(column);
            property = lambdaProperty(column);
            humpToLine = lambdaPropertyForHumpToLine(column);

            select.append(tableClassNameAliasMap.get(className));
            select.append(".");
            select.append(humpToLine);
            alias = propertyAliasMap.get(className+"_"+property);
            if (alias != null) {
                select.append(" as " );
                select.append(alias);
            } else {
                if (!humpToLine.equals(property)) {
                    select.append(" as " );
                    select.append(property);
                }
            }
            if(size != 1){
                select.append(",");
            }
            size--;

        }
        return select;
    }
}
