package com.zl.easyexcel.write.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangliang
 * @date 2021/11/23.
 */
@Data
public class SimpleTableHeadCreator {
    private List<List<String>> list;
    public SimpleTableHeadCreator(){
        this.list = new ArrayList<>();
    }
    public SimpleTableHeadCreator addHead(String head){
        List<String> heads = new ArrayList<>();
        heads.add(head);
        this.list.add(heads);
        return this;
    }
    public SimpleTableHeadCreator addHead(String ...headArr){
        for (String head : headArr) {
            List<String> heads = new ArrayList<>();
            heads.add(head);
            this.list.add(heads);
        }
        return this;
    }
}
