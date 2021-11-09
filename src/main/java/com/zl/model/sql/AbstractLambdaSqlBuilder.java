package com.zl.model.sql;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zhangliang
 * @date 2021/11/6.
 */
public abstract class AbstractLambdaSqlBuilder<C extends AbstractLambdaSqlBuilder> extends AbstractSqlBuilder<SqlFunction<?, ?>,C> {

    /**
     * key class name,value alias
     */
    protected final Map<Class<?>, String> tableMap = new HashMap<>(16);
    protected final Map<Class<?>, String> tableAliasMap = new HashMap<>(16);
    protected final Map<String, String> tableClassNameAliasMap = new HashMap<>(16);

    protected final Map<String, String> propertyAliasMap = new HashMap<>(16);

    public C leftJoin(Class<?> table){
        return leftJoin(table,null);
    }

    public C leftJoin(Class<?> table,String alias){
        return leftJoin(null,alias,table);
    }

    public C leftJoin(String table,String alias,Class<?> tableClass){
        return join(table,tableClass,alias,"left join");
    }

    public C rightJoin(Class<?> table){
        return rightJoin(table,null);
    }

    public C rightJoin(Class<?> table,String alias){
        return rightJoin(null,alias,table);
    }

    public C rightJoin(String table,String alias,Class<?> tableClass){
        return join(table,tableClass,alias,"right join");
    }

    public C innerJoin(Class<?> table){
        return rightJoin(table,null);
    }

    public C innerJoin(Class<?> table,String alias){
        return innerJoin(null,alias,table);
    }

    public C innerJoin(String table,String alias,Class<?> tableClass){
        return join(table,tableClass,alias,"inner join");
    }

    private C join(String table,Class<?> tableClass,String alias,String joinType){
        analysisTable(tableClass,alias,table);
        sqlBd.append(" ");
        sqlBd.append(joinType);
        sqlBd.append(" ");
        sqlBd.append(tableMap.get(tableClass));
        sqlBd.append(" ");
        sqlBd.append(tableAliasMap.get(tableClass));
        return typedThis;
    }

    public <T,R,K,V> C on(SqlFunction<T,R> column0,SqlFunction<K,V> column1){
        sqlBd.append(" on ");
        appendColumn(column0);
        sqlBd.append(" = ");
        appendColumn(column1);
        return typedThis;
    }

    public <T, R> C eq(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.EQ,column,value);
        return typedThis;
    }

    public <T, R> C ne(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.NE,column,value);
        return typedThis;
    }

    public <T, R> C ge(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.GE,column,value);
        return typedThis;
    }

    public <T, R> C gt(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.GT,column,value);
        return typedThis;
    }

    public <T, R> C le(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LE,column,value);
        return typedThis;
    }

    public <T, R> C lt(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LT,column,value);
        return typedThis;
    }

    public <T, R> C isNull(SqlFunction<T, R> column) {
        appendWhereCondition(SymbolEnum.IS_NULL,column,null);
        return typedThis;
    }

    public <T, R> C isNotNull(SqlFunction<T, R> column) {
        appendWhereCondition(SymbolEnum.IS_NOT_NULL,column,null);
        return typedThis;
    }

    public <T, R> C like(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE,column,value);
        return typedThis;
    }

    public <T, R> C notLike(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.NOT_LIKE,column,value);
        return typedThis;
    }

    public <T, R> C likeLeft(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE_LEFT,column,value);
        return typedThis;
    }

    public <T, R> C likeRight(SqlFunction<T, R> column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE_RIGHT,column,value);
        return typedThis;
    }

    public <T, R> C in(SqlFunction<T, R> column, Collection<?> values) {
        appendWhereCondition(SymbolEnum.IN,column,values);
        return typedThis;
    }

    public <T, R> C notIn(SqlFunction<T, R> column, Collection<?> values) {
        appendWhereCondition(SymbolEnum.NOT_IN,column,values);
        return typedThis;
    }

    public <T, R> C between(SqlFunction<T, R> column, Object value1,Object value2) {
        appendWhereCondition(SymbolEnum.BETWEEN,column, Arrays.asList(value1,value2));
        return typedThis;
    }

    public <T, R> C notBetween(SqlFunction<T, R> column, Object value1,Object value2) {
        appendWhereCondition(SymbolEnum.NOT_BETWEEN,column, Arrays.asList(value1,value2));
        return typedThis;
    }


    @Override
    protected void appendColumn(SqlFunction<?, ?> column){
        sqlBd.append(column(column));
    }

    @Override
    protected String column(SqlFunction<?, ?> column) {
        return tableClassNameAliasMap.get(lambdaClassName(column))+"."+lambdaPropertyForHumpToLine(column);
    }

    protected String lambdaProperty(SqlFunction<?,?> func){
        String implMethodName = func.getImplMethodName();
        String name = implMethodName.substring(3);
        return name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
    }

    protected String lambdaPropertyForHumpToLine(SqlFunction<?,?> func){
        return humpToLine(lambdaProperty(func));
    }

    protected String lambdaClassName(SqlFunction<?,?> func){
        String implClass = func.getImplClass();
        return implClass.substring(implClass.lastIndexOf('/')+1);
    }

    protected void analysisTable(Class<?> tableClass,String alias,String table){
        String name = tableClass.getSimpleName();
        if (StringUtils.isBlank(table)) {
            table = humpToLine(name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1));
        }
        tableMap.put(tableClass,table);
        if (StringUtils.isBlank(alias)) {
            tableAliasMap.put(tableClass,table+"_0");
            tableClassNameAliasMap.put(name,table+"_0");
        }else {
            tableAliasMap.put(tableClass,alias);
            tableClassNameAliasMap.put(name,alias);
        }
    }
}
