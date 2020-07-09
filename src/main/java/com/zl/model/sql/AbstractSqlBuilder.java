package com.zl.model.sql;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
public abstract class AbstractSqlBuilder<C extends AbstractSqlBuilder> {

    /**
     * 占位符
     */
    final C typedThis = ( C ) this;

    static final String  OR = " or ";
    static final String AND = " and ";
    String connect = AND;
    boolean isConnect = true;

    static final String ASC = "asc";
    static final String DESC = "desc";
    
    @Getter
    protected List<Object> params = new ArrayList<>();

    StringBuilder sqlBd = new StringBuilder();

    boolean orderBy = false;
    boolean groupBy = false;

    AbstractSqlBuilder() {}

    public C where() {
        sqlBd.append(" where 1=1");
        return typedThis;
    }

    public abstract String sql();

    String in(Collection<?> list){
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            builder.append("?,");
        }
        String str = builder.toString();
        return str.substring(0,str.length()-1);
    }

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
}
