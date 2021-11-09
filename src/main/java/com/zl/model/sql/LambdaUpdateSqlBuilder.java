package com.zl.model.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/11/6.
 */
public class LambdaUpdateSqlBuilder extends AbstractLambdaSqlBuilder<LambdaUpdateSqlBuilder> {

    private final List<SqlFunction<?, ?>> sqlSetColumns = new ArrayList<>();

    private boolean set = false;

    public LambdaUpdateSqlBuilder update(Class<?> table){
        return update(table,null);
    }

    public LambdaUpdateSqlBuilder update(Class<?> table,String alias){
        return update(null,alias,table);
    }

    public LambdaUpdateSqlBuilder update(String table,String alias,Class<?> tableClass){
        analysisTable(tableClass,alias,table);
        sqlBd.append("update ");
        sqlBd.append(tableMap.get(tableClass));
        sqlBd.append(" ");
        sqlBd.append(tableAliasMap.get(tableClass));
        return this;
    }

    public <T, R> LambdaUpdateSqlBuilder set(SqlFunction<T, R> column, Object val){
        set();
        sqlSetColumns.add(column);
        params.add(val);
        return this;
    }

    public <T, R> LambdaUpdateSqlBuilder setAndValue(SqlFunction<T, R> column, Object val){
        set();
        sqlSetColumns.add(column);
        params.add(val);
        return this;
    }

    private void set(){
        if(!set){
            sqlBd.append("${setSql}");
            set = true;
        }
    }


    @Override
    public String sql() {
        return sqlBd.toString()
                .replace("${setSql}",getSet().toString())
                .replace(" or ()","")
                .replace(" and ()","");
     }

    private StringBuilder getSet(){
        StringBuilder set = new StringBuilder(" set ");
        String humpToLine;
        String className;
        int size = sqlSetColumns.size();
        for (SqlFunction<?, ?> column : sqlSetColumns) {
            className = lambdaClassName(column);
            humpToLine = lambdaPropertyForHumpToLine(column);

            set.append(tableClassNameAliasMap.get(className));
            set.append(".");
            set.append(humpToLine);
            set.append(" = ?");
            if(size != 1){
                set.append(", ");
            }
            size--;

        }
        return set;
    }
}
