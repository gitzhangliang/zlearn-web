package com.zl.easyexcel.write.table;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MergeTableHeadCreator {
    private List<List<String>> list;
    private int mergeRowNum;
    public MergeTableHeadCreator(int mergeRowNum){
        this.list = new ArrayList<>();
        this.mergeRowNum = mergeRowNum;
    }
    public MergeTableHeadCreator addHead(String ...headArr){
        List<String> heads = new ArrayList<>();
        int index = 1;
        for (String head : headArr) {
            heads.add(head);
            if(index == mergeRowNum){
                this.list.add(heads);
                index = 1;
                heads = new ArrayList<>();
            }else{
                index++;
            }
        }
        return this;
    }
}
