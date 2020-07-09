package com.zl.model.sql;

import lombok.Getter;

/**
 * @author zhangliang
 * @date 2020/7/7.
 */
@Getter
public enum SymbolEnum {

    EQ("="),
    NE("!="),
    GE(">="),
    GT(">"),
    LE("<="),
    LT("<");
    private String name;

    SymbolEnum(String name){
        this.name = name;
    }
}
