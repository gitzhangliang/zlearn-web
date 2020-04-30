package com.zl.easyexcel.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.zl.easyexcel.merge.MergeTypeEnum;
import com.zl.easyexcel.merge.TextSameMergeStrategy;
import lombok.Data;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Data
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    public static void main(String[] args) {
//            mergeWrite();
        mergeWriteByTextSame();

    }
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            if(i>5){
                data.setDoubleData(0.56);

            }else {
                data.setDoubleData(0.57);

            }
            list.add(data);
        }
        return list;
    }
    public static void mergeWrite() {
        String fileName = TestFileUtil.getPath() + "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("模板").doWrite(data());
    }

    public static void mergeWriteByTextSame() {
        String fileName = TestFileUtil.getPath() + "mergeWriteByTextSame" + System.currentTimeMillis() + ".xlsx";
        // 可以按照合并类型，指定是水平合并还是垂直合并，内容相同的会进行合并, 需要传入 行总数 列总数
        TextSameMergeStrategy textSameMergeStrategy = new TextSameMergeStrategy(4, data().size(),
                MergeTypeEnum.VERTICAL_MERGE,false,false,Arrays.asList(2),Arrays.asList(2));
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(textSameMergeStrategy).sheet("模板").doWrite(data());
    }

}