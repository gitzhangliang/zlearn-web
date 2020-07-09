package com.zl.model.sql;


import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public class LambdaSqlBuilder extends AbstractSqlBuilder<LambdaSqlBuilder> {

    /**
     * key class name,value alias
     */
    private final Map<Class<?>, String> tableAliasMap = new HashMap<>(16);
    private final Map<Class<?>, String> tableMap = new HashMap<>(16);
    private final Map<String, String> tableClassNameAliasMap = new HashMap<>(16);

    private final Map<String, String> propertyAliasMap = new HashMap<>(16);
    private final List<SqlFunction<?,?>> selected = new ArrayList<>();


    private static Pattern humpPattern = Pattern.compile("[A-Z]");



    @SafeVarargs
    public final <T,R>  LambdaSqlBuilder select(SqlFunction<T, R> ...columns){
        selected.addAll(Arrays.asList(columns));
        return this;
    }

    public <T,R,K,V>LambdaSqlBuilder selectAlias(SqlFunction<T,R> column,SqlFunction<K,V> alias){
        selected.add(column);
        String property = lambdaProperty(column);
        String className = lambdaClassName(column);
        String propertyAlias = lambdaProperty(alias);
        propertyAliasMap.put(className+"_"+property,propertyAlias);
        return this;
    }

    public LambdaSqlBuilder from(Class<?> table){
        return from(table,null);
    }
    
    public LambdaSqlBuilder from(Class<?> table,String alias){
        analysisTable(table,alias);
        sqlBd.append(" from ");
        sqlBd.append(tableMap.get(table));
        sqlBd.append(" ");
        sqlBd.append(tableAliasMap.get(table));
        return this;
    }

    public LambdaSqlBuilder leftJoin(Class<?> table){
        return leftJoin(table,null);
    }

    public LambdaSqlBuilder leftJoin(Class<?> table,String alias){
        join(table,alias,"left join");
        return this;
    }

    public LambdaSqlBuilder rightJoin(Class<?> table){
        return rightJoin(table,null);
    }

    public LambdaSqlBuilder rightJoin(Class<?> table,String alias){
        join(table,alias,"right join");
        return this;
    }

    public LambdaSqlBuilder innerJoin(Class<?> table){
        return rightJoin(table,null);
    }

    public LambdaSqlBuilder innerJoin(Class<?> table,String alias){
        join(table,alias,"inner join");
        return this;
    }

    private void join(Class<?> table,String alias,String joinType){
        analysisTable(table,alias);
        sqlBd.append(" ");
        sqlBd.append(joinType);
        sqlBd.append(" ");
        sqlBd.append(tableMap.get(table));
        sqlBd.append(" ");
        sqlBd.append(tableAliasMap.get(table));
    }

    public <T,R,K,V>LambdaSqlBuilder on(SqlFunction<T,R> column0,SqlFunction<K,V> column1){
        sqlBd.append(" on ");
        column(column0);
        sqlBd.append(" = ");
        column(column1);
        return this;
    }

    public <T, R> LambdaSqlBuilder eq(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.EQ,column,value);
        return this;
    }

    public <T, R> LambdaSqlBuilder ne(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.NE,column,value);
        return this;
    }

    public <T, R> LambdaSqlBuilder ge(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.GE,column,value);
        return this;
    }

    public <T, R> LambdaSqlBuilder gt(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.GT,column,value);
        return this;
    }

    public <T, R> LambdaSqlBuilder le(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LE,column,value);
        return this;
    }

    public <T, R> LambdaSqlBuilder lt(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LT,column,value);
        return this;
    }


    public <T, R> LambdaSqlBuilder isNull(SqlFunction<T, R> column) {
        connectColumn(column);
        sqlBd.append("is Null");
        return this;
    }


    public <T, R> LambdaSqlBuilder isNotNull(SqlFunction<T, R> column) {
        connectColumn(column);
        sqlBd.append("is not Null");
        return this;
    }

    private  <T, R> void handleLike(SqlFunction<T, R> column, Object value, Consumer<LambdaSqlBuilder> consumer) {
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append("like ?");
            consumer.accept(this);
        }
    }

    public <T, R> LambdaSqlBuilder like(SqlFunction<T, R> column, Object value) {
        handleLike(column,value,v->v.params.add("%"+value+"%"));
        return this;
    }

    public <T, R> LambdaSqlBuilder notLike(SqlFunction<T, R> column, Object value) {
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append("not like ?");
            params.add("%"+value+"%");
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder likeLeft(SqlFunction<T, R> column, Object value) {
        handleLike(column,value,v->v.params.add("%"+value));
        return this;
    }

    public <T, R> LambdaSqlBuilder likeRight(SqlFunction<T, R> column, Object value) {
        handleLike(column,value,v->v.params.add(value+"%"));
        return this;
    }

    public <T, R> LambdaSqlBuilder in(SqlFunction<T, R> column, Collection<?> values) {
        if(values != null && !values.isEmpty()){
            connectColumn(column);
            sqlBd.append("in(");
            sqlBd.append(in(values));
            sqlBd.append(")");
            params.addAll(values);
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder notIn(SqlFunction<T, R> column, Collection<?> values) {
        if(values != null && !values.isEmpty()){
            connectColumn(column);
            sqlBd.append("not in(");
            sqlBd.append(in(values));
            sqlBd.append(")");
            params.addAll(values);
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder inSql(SqlFunction<T, R> column, String inSql) {
        if(inSql != null && !"".equals(inSql)){
            connectColumn(column);
            sqlBd.append("in(");
            sqlBd.append(inSql);
            sqlBd.append(")");
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder notInSql(SqlFunction<T, R> column, String inSql) {
        if(inSql != null && !"".equals(inSql)){
            connectColumn(column);
            sqlBd.append("not in(");
            sqlBd.append(inSql);
            sqlBd.append(")");
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder between(SqlFunction<T, R> column, Object value1,Object value2) {
        if(value1 != null && !"".equals(value1) && value2 != null && !"".equals(value2)){
            connectColumn(column);
            sqlBd.append("between ? and ?");
            params.add(value1);
            params.add(value2);
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder notBetween(SqlFunction<T, R> column, Object value1,Object value2) {
        if(value1 != null && !"".equals(value1) && value2 != null && !"".equals(value2)){
            connectColumn(column);
            sqlBd.append("not between ? and ?");
            params.add(value1);
            params.add(value2);
        }
        return this;
    }

    public <T, R> LambdaSqlBuilder orderByAsc(SqlFunction<T, R> column) {
        orderBy(ASC,column);
        return this;
    }

    @SafeVarargs
    public final <T, R> LambdaSqlBuilder orderByAsc(SqlFunction<T, R> ...columns) {
        orderBy(ASC,columns);
        return this;
    }
    @SafeVarargs
    private final <T, R> void orderBy(String dir,SqlFunction<T, R> ...columns) {
        if(!orderBy){
            sqlBd.append(" order by ");
        }
        for (SqlFunction<T, R> column : columns) {
            if(orderBy){
                sqlBd.append(", ");
            }
            orderBy = true;
            column(column);
            if(DESC.equals(dir)){
                sqlBd.append(" desc");
            }
        }
    }

    public <T, R> LambdaSqlBuilder orderByDesc(SqlFunction<T, R> column) {
        orderBy(DESC,column);
        return this;
    }

    @SafeVarargs
    public final <T, R> LambdaSqlBuilder orderByDesc(SqlFunction<T, R> ...columns) {
        orderBy(DESC,columns);
        return this;
    }

    public <T, R> LambdaSqlBuilder groupBy(SqlFunction<T, R> column) {
        groupBy(groupBy,column);
        return this;
    }

    @SafeVarargs
    public final <T, R> LambdaSqlBuilder groupBy(SqlFunction<T, R>... columns) {
        groupBy(groupBy,columns);
        return this;
    }
    @SafeVarargs
    private final <T, R> void groupBy(boolean hadGroupBy,SqlFunction<T, R> ...columns) {
        if(!hadGroupBy){
            sqlBd.append(" group by ");
        }
        for (SqlFunction<T, R> column : columns) {
            if(hadGroupBy){
                sqlBd.append(", ");
            }
            hadGroupBy = true;
            groupBy = true;
            column(column);
        }
    }


    @Override
    public String sql() {
        StringBuilder select = getSelect();
        return select.append(sqlBd).toString()
                .replace(" 1=1 and", "")
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

    /** 驼峰转下划线*/
    private String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String lambdaProperty(SqlFunction<?,?> func){
        String implMethodName = func.getImplMethodName();
        String name = implMethodName.substring(3);
        return name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
    }

    private String lambdaPropertyForHumpToLine(SqlFunction<?,?> func){
        String implMethodName = func.getImplMethodName();
        String name = implMethodName.substring(3);
        return humpToLine(name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1));
    }

    private String lambdaClassName(SqlFunction<?,?> func){
        String implClass = func.getImplClass();
        return implClass.substring(implClass.lastIndexOf('/')+1);
    }

    private void analysisTable(Class<?> table,String alias){
        String name = table.getSimpleName();
        String lineName = humpToLine(name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1));
        tableMap.put(table,lineName);
        if (StringUtils.isBlank(alias)) {
            tableAliasMap.put(table,lineName+"_0");
            tableClassNameAliasMap.put(name,lineName+"_0");
        }else {
            tableAliasMap.put(table,alias);
            tableClassNameAliasMap.put(name,alias);

        }
    }

    private <T, R> void appendWhereCondition(SymbolEnum symbol, SqlFunction<T, R> column, Object value){
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append(symbol.getName());
            sqlBd.append(" ?");
            params.add(value);
        }
    }

    public <T, R> void connectColumn(SqlFunction<T, R> column) {
        if (isConnect) {
            sqlBd.append(connect);
        }else {
            isConnect = true;
        }
        column(column);
        sqlBd.append(" ");
        if (connect.equals(OR)) {
            connect = AND;
        }
    }

    private <T, R>void column(SqlFunction<T, R> column){
        sqlBd.append(tableClassNameAliasMap.get(lambdaClassName(column)));
        sqlBd.append(".");
        sqlBd.append(lambdaPropertyForHumpToLine(column));
    }

}
