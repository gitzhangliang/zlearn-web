package com.zl.easyexcel;

import lombok.Data;

import java.util.List;

/**
 * @author zl
 * @date 2019/11/15.
 */
@Data
public class ImportExcelResult<T,E> {
    boolean success;
    List<T> data;
    List<E> error;
}
