package com.zl.model.sql;


import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public class StringSqlBuilder extends AbstractSqlBuilder<StringSqlBuilder> {


    public StringSqlBuilder select(String select){
        sqlBd.append("select ");
        sqlBd.append(select);
        return typedThis;
    }

    public StringSqlBuilder from(String table){
        sqlBd.append(" from ");
        sqlBd.append(table);
        return typedThis;
    }

    public StringSqlBuilder leftJoin(String table){
        sqlBd.append(" left join ");
        sqlBd.append(table);
        return typedThis;
    }

    public StringSqlBuilder rightJoin(String table){
        sqlBd.append(" right join ");
        sqlBd.append(table);
        return typedThis;
    }
    public StringSqlBuilder innerJoin(String table){
        sqlBd.append(" inner join ");
        sqlBd.append(table);
        return typedThis;
    }
    public StringSqlBuilder on(String column0,String column1){
        sqlBd.append(" on ");
        sqlBd.append(column0);
        sqlBd.append(" = ");
        sqlBd.append(column1);
        return typedThis;
    }

    public StringSqlBuilder orderBy(String orderBy) {
        sqlBd.append(" order by " );
        sqlBd.append(orderBy);
        return typedThis;
    }

    public StringSqlBuilder groupBy(String groupBy){
        sqlBd.append(" group by ");
        sqlBd.append(groupBy);
        return typedThis;
    }


    public StringSqlBuilder eq(String column, Object value) {
        appendWhereCondition(SymbolEnum.EQ,column,value);
        return this;
    }

    public StringSqlBuilder ne(String column, Object value) {
        appendWhereCondition(SymbolEnum.NE,column,value);
        return this;
    }


    public StringSqlBuilder ge(String column, Object value) {
        appendWhereCondition(SymbolEnum.GE,column,value);
        return this;
    }

    public StringSqlBuilder gt(String column, Object value) {
        appendWhereCondition(SymbolEnum.GT,column,value);
        return this;
    }

    public StringSqlBuilder le(String column, Object value) {
        appendWhereCondition(SymbolEnum.LE,column,value);
        return this;
    }

    public StringSqlBuilder lt(String column, Object value) {
        appendWhereCondition(SymbolEnum.LT,column,value);
        return this;
    }

    public StringSqlBuilder isNull(String column) {
        connectColumn(column);
        sqlBd.append("is Null");
        return this;
    }


    public StringSqlBuilder isNotNull(String column) {
        connectColumn(column);
        sqlBd.append("is not Null");
        return this;
    }


    private  void handleLike(String column, Object value, Consumer<StringSqlBuilder> consumer) {
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append("like ?");
            consumer.accept(this);
        }
    }

    public StringSqlBuilder like(String column, Object value) {
        handleLike(column,value,v->v.params.add("%"+value+"%"));
        return this;
    }

    public StringSqlBuilder notLike(String column, Object value) {
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append("not like ?");
            params.add("%"+value+"%");
        }
        return this;
    }

    public StringSqlBuilder likeLeft(String column, Object value) {
        handleLike(column,value,v->v.params.add("%"+value));
        return this;
    }

    public StringSqlBuilder likeRight(String column, Object value) {
        handleLike(column,value,v->v.params.add(value+"%"));
        return this;
    }

    public StringSqlBuilder in(String column, Collection<?> values) {
        handleIn(column,values,"in");
        return this;
    }

    public StringSqlBuilder notIn(String column, Collection<?> values) {
        handleIn(column,values,"not in");
        return this;
    }

    private void handleIn(String column, Collection<?> values,String in) {
        if(values != null && !values.isEmpty()){
            connectColumn(column);
            sqlBd.append(in);
            sqlBd.append("(");
            sqlBd.append(in(values));
            sqlBd.append(")");
            params.addAll(values);
        }
    }

    public StringSqlBuilder inSql(String column, String inSql) {
        if(inSql != null && !"".equals(inSql)){
            connectColumn(column);
            sqlBd.append("in(");
            sqlBd.append(inSql);
            sqlBd.append(")");
        }
        return this;
    }

    public StringSqlBuilder notInSql(String column, String inSql) {
        if(inSql != null && !"".equals(inSql)){
            connectColumn(column);
            sqlBd.append("not in(");
            sqlBd.append(inSql);
            sqlBd.append(")");
        }
        return this;
    }


    public StringSqlBuilder between(String column, Object value1,Object value2) {
        handleBetween(column,value1,value2,"between");
        return this;
    }

    public StringSqlBuilder notBetween(String column, Object value1,Object value2) {
        handleBetween(column,value1,value2,"not between");
        return this;
    }

    private void handleBetween(String column, Object value1,Object value2,String between){
        if(value1 != null && !"".equals(value1) && value2 != null && !"".equals(value2)){
            connectColumn(column);
            sqlBd.append(between);
            sqlBd.append(" ? and ?");
            params.add(value1);
            params.add(value2);
        }
    }

    @Override
    public String sql() {
        return sqlBd.toString()
                .replace(" 1=1 and", "")
                .replace(" or ()","")
                .replace(" and ()","");
    }

    private void appendWhereCondition(SymbolEnum symbol, String column, Object value){
        if(value != null && !"".equals(value)){
            connectColumn(column);
            sqlBd.append(symbol.getName());
            sqlBd.append(" ?");
            params.add(value);
        }
    }

    private void connectColumn(String column) {
        if (isConnect) {
            sqlBd.append(connect);
        }else {
            isConnect = true;
        }
        sqlBd.append(column);
        sqlBd.append(" ");
        if (connect.equals(OR)) {
            connect = AND;
        }
    }
}
