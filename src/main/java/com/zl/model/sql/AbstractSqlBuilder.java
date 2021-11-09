package com.zl.model.sql;

import lombok.Getter;
import org.apache.poi.ss.formula.functions.T;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public abstract class AbstractSqlBuilder<R,C extends AbstractSqlBuilder> {

    /**
     * 占位符
     */
    final C typedThis = ( C ) this;

    private static final String  OR = " or ";
    private static final String AND = " and ";

    private String connect = AND;
    private boolean isConnect = true;

    static final String ASC = "asc";
    static final String DESC = "desc";

    private boolean appendWhereCondition = false;


    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");


    StringBuilder sqlBd = new StringBuilder();

    @Getter
    protected List<Object> params = new ArrayList<>();

    protected final List<R> groupByColumns = new ArrayList<>();
    protected final List<Sort<R>> orderByColumns = new ArrayList<>();


    AbstractSqlBuilder() {}

    public C where() {
        sqlBd.append(" where ");
        return typedThis;
    }

    public abstract String sql();
    protected abstract void appendColumn(R column);
    protected abstract String column(R column);

    public C and(Consumer<C> consumer) {
        isConnect = false;
        sqlBd.append(" and (");
        consumer.accept(typedThis);
        sqlBd.append(")");
        isConnect = true;
        return typedThis;
    }

    public C or() {
        connect = OR;
        return typedThis;
    }

    public C or(Consumer<C> consumer) {
        isConnect = false;
        sqlBd.append(" or (");
        consumer.accept(typedThis);
        sqlBd.append(")");
        isConnect = true;
        return typedThis;
    }

    protected void connectColumn(R column) {
        if (isConnect && appendWhereCondition) {
            sqlBd.append(connect);
        }else {
            isConnect = true;
        }
        appendColumn(column);
        sqlBd.append(" ");
        if (connect.equals(OR)) {
            connect = AND;
        }
        appendWhereCondition = true;
    }

    protected void appendWhereCondition(SymbolEnum symbol, R column, Object value){
        if(Boolean.TRUE.equals(symbol.getHasValue()) && value != null && !"".equals(value)){
            if(value instanceof Collection){
                Collection<?> values = (Collection<?>) value;
                if(values.isEmpty() || values.stream().anyMatch(Objects::isNull)){
                    return;
                }
                params.addAll((Collection<?>)symbol.valueFormat(values));
            }else{
                params.add(symbol.valueFormat(value));
            }
            connectColumn(column);
            sqlBd.append(symbol.getName());
            sqlBd.append(" ");
            sqlBd.append(symbol.placeholderValue(value));
        }else if(Boolean.FALSE.equals(symbol.getHasValue())){
            connectColumn(column);
            sqlBd.append(symbol.getName());
        }
    }

    protected void appendGroupBy() {
        if (groupByColumns.isEmpty()) {
            return;
        }
        sqlBd.append(" group by ");
        int size = groupByColumns.size();
        for (R column : groupByColumns) {
            appendColumn(column);
            if (size != 1) {
                sqlBd.append(", ");
            }
            size--;
        }
    }

    protected void appendOrderBy() {
        if (orderByColumns.isEmpty()) {
            return;
        }
        sqlBd.append(" order by ");
        int size = orderByColumns.size();
        for (Sort<R> sort : orderByColumns) {
            appendColumn(sort.column);
            if(DESC.equals(sort.dir)){
                sqlBd.append(" ");
                sqlBd.append(sort.dir);
            }
            if (size != 1) {
                sqlBd.append(", ");
            }
            size--;
        }
    }

    protected static class Sort<R>{
        R column;
        String dir;
        Sort(R column,String dir){
            this.column = column;
            this.dir = dir;
        }
    }

    public C appendSql(String sql) {
        sqlBd.append(sql);
        return typedThis;
    }

    public C unionAll(String sql) {
        sqlBd.append(" union all ");
        sqlBd.append(sql);
        return typedThis;
    }

    public C having(String havingSql) {
        sqlBd.append(" having ");
        sqlBd.append(havingSql);
        return typedThis;
    }


    public String sqlAndReplacePlaceholder(){
        String sql = sql();
        for (Object param : params) {
            if(param == null){
                sql = sql.replaceFirst("\\?","null");
            }else if (param instanceof String) {
                sql = sql.replaceFirst("\\?","\""+(String)param +"\"");
            }else if(param instanceof Number){
                sql = sql.replaceFirst("\\?",param.toString());
            }else if(param instanceof Boolean){
                sql = sql.replaceFirst("\\?",Boolean.TRUE.equals(param) ? "1":"0");
            }else if(param instanceof Date){
                sql = sql.replaceFirst("\\?",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(param));
            }
        }
        return sql;
    }

    /** 驼峰转下划线*/
    protected String humpToLine(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
