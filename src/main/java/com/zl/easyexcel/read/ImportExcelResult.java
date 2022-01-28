package com.zl.easyexcel.read;

import lombok.Data;

import java.util.List;

/**
 * @author zhangliang
 * @date 2019/11/15.
 */
@Data
public class ImportExcelResult<T,E> {
    boolean success;
    List<T> data;
    List<E> error;
}
