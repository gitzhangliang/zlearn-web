package com.zl.model.sql;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;

@FunctionalInterface
public interface SqlFunction<T, R> extends Function<T, R>, Serializable {

    default SerializedLambda getSerializedLambda(){
        Method write;
        try {
            write = this.getClass().getDeclaredMethod("writeReplace");
            write.setAccessible(true);
            return (SerializedLambda) write.invoke(this);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    default String getImplClass() {
        return getSerializedLambda().getImplClass();
    }

    default String getImplMethodName() {
        return getSerializedLambda().getImplMethodName();
    }
}
