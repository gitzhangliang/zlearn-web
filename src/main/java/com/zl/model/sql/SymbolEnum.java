package com.zl.model.sql;

import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
@Getter
public enum SymbolEnum {
    //
    EQ("=",true),
    NE("!=",true),
    GE(">=",true),
    GT(">",true),
    LE("<=",true),
    LT("<",true),
    IS_NULL("is null",false),
    IS_NOT_NULL("is not null",false),
    LIKE("like",true){
        @Override
        public String valueFormat(Object value) {
            return "%"+value+"%";
        }
    },
    LIKE_LEFT("like",true){
        @Override
        public String valueFormat(Object value) {
            return "%"+value;
        }
    },
    LIKE_RIGHT("like",true){
        @Override
        public String valueFormat(Object value) {
            return value+"%";
        }
    },
    NOT_LIKE("not like",true){
        @Override
        public String valueFormat(Object value) {
            return "%"+value+"%";
        }
    },
    IN("in",true){
        @Override
        public String placeholderValue(Object value) {
            Collection<?> values = ( Collection<?> ) value;
            List<String> collect = values.stream().map(v -> "?").collect(Collectors.toList());
            String join = String.join(",", collect);
            return"("+join+")";
        }
    },
    NOT_IN("not in",true){
        @Override
        public String placeholderValue(Object value) {
            return SymbolEnum.IN.placeholderValue(value);
        }
    },
    BETWEEN("between",true){
        @Override
        public String placeholderValue(Object value) {
            return "? and ?";
        }
    },
    NOT_BETWEEN("between",true){
        @Override
        public String placeholderValue(Object value) {
            return "? and ?";
        }
    };
    private final String name;
    private final Boolean hasValue;

    SymbolEnum(String name,Boolean hasValue){

        this.name = name;
        this.hasValue = hasValue;
    }
    public String placeholderValue(Object value){return "?";}

    public Object valueFormat(Object value){return value;}

}
