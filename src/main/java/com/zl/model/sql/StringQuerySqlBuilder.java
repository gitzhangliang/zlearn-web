package com.zl.model.sql;


import java.util.Arrays;
import java.util.Collection;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public class StringQuerySqlBuilder extends AbstractSqlBuilder<String,StringQuerySqlBuilder> {


    public StringQuerySqlBuilder select(String select){
        sqlBd.append("select ");
        sqlBd.append(select);
        return this;
    }

    public StringQuerySqlBuilder from(String table){
        sqlBd.append(" from ");
        sqlBd.append(table);
        return this;
    }

    public StringQuerySqlBuilder leftJoin(String table){
        sqlBd.append(" left join ");
        sqlBd.append(table);
        return this;
    }

    public StringQuerySqlBuilder rightJoin(String table){
        sqlBd.append(" right join ");
        sqlBd.append(table);
        return this;
    }
    public StringQuerySqlBuilder innerJoin(String table){
        sqlBd.append(" inner join ");
        sqlBd.append(table);
        return this;
    }
    public StringQuerySqlBuilder on(String column0,String column1){
        sqlBd.append(" on ");
        sqlBd.append(column0);
        sqlBd.append(" = ");
        sqlBd.append(column1);
        return this;
    }


    public StringQuerySqlBuilder eq(String column, Object value) {
        appendWhereCondition(SymbolEnum.EQ,column,value);
        return this;
    }

    public StringQuerySqlBuilder ne(String column, Object value) {
        appendWhereCondition(SymbolEnum.NE,column,value);
        return this;
    }

    public StringQuerySqlBuilder ge(String column, Object value) {
        appendWhereCondition(SymbolEnum.GE,column,value);
        return this;
    }

    public StringQuerySqlBuilder gt(String column, Object value) {
        appendWhereCondition(SymbolEnum.GT,column,value);
        return this;
    }

    public StringQuerySqlBuilder le(String column, Object value) {
        appendWhereCondition(SymbolEnum.LE,column,value);
        return this;
    }

    public StringQuerySqlBuilder lt(String column, Object value) {
        appendWhereCondition(SymbolEnum.LT,column,value);
        return this;
    }

    public StringQuerySqlBuilder isNull(String column) {
        appendWhereCondition(SymbolEnum.IS_NULL,column,null);
        return this;
    }


    public StringQuerySqlBuilder isNotNull(String column) {
        appendWhereCondition(SymbolEnum.IS_NOT_NULL,column,null);
        return this;
    }


    public StringQuerySqlBuilder like(String column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE,column,value);
        return this;
    }

    public StringQuerySqlBuilder notLike(String column, Object value) {
        appendWhereCondition(SymbolEnum.NOT_LIKE,column,value);
        return this;
    }

    public StringQuerySqlBuilder likeLeft(String column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE_LEFT,column,value);
        return this;
    }

    public StringQuerySqlBuilder likeRight(String column, Object value) {
        appendWhereCondition(SymbolEnum.LIKE_RIGHT,column,value);
        return this;
    }

    public StringQuerySqlBuilder in(String column, Collection<?> values) {
        appendWhereCondition(SymbolEnum.IN,column,values);
        return this;
    }

    public StringQuerySqlBuilder notIn(String column, Collection<?> values) {
        appendWhereCondition(SymbolEnum.NOT_IN,column,values);
        return this;
    }


    public StringQuerySqlBuilder between(String column, Object value1,Object value2) {
        appendWhereCondition(SymbolEnum.BETWEEN,column, Arrays.asList(value1,value2));
        return this;
    }

    public StringQuerySqlBuilder notBetween(String column, Object value1,Object value2) {
        appendWhereCondition(SymbolEnum.NOT_BETWEEN,column, Arrays.asList(value1,value2));
        return this;
    }


    public StringQuerySqlBuilder orderByAsc(String... columns) {
        for (String column : columns) {
            orderByColumns.add(new Sort<>(column,ASC));
        }
        return this;
    }

    public StringQuerySqlBuilder orderByDesc(String... columns) {
        for (String column : columns) {
            orderByColumns.add(new Sort<>(column,DESC));
        }
        return this;
    }

    public StringQuerySqlBuilder groupBy(String... columns){
        groupByColumns.addAll(Arrays.asList(columns));
        return this;
    }



    @Override
    public String sql() {
        appendOrderBy();
        appendGroupBy();
        return sqlBd.toString()
                .replace(" or ()","")
                .replace(" and ()","");
    }


    @Override
    protected void appendColumn(String column) {
        sqlBd.append(column);
    }

    @Override
    protected String column(String column) {
        return column;
    }
}
