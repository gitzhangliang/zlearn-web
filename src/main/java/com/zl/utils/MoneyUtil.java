package com.zl.utils;

import java.math.BigDecimal;

/**
 * @author zhangliang
 * @date 2019/11/21.
 */
public class MoneyUtil {
    private MoneyUtil(){}

    /**
     * 汉字的数字
     */
    private static final String[] CN_NUMS = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    /**
     * 基本单位
     */
    private static final String[] CN_INT_RADICAL = new String[]{"", "拾", "佰", "仟"};
    /**
     * 对应整数部分扩展单位
     */
    private static final String[] CN_INT_UNITS = new String[]{"", "万", "亿", "兆"};

    /**
     * 对应小数部分单位
     */
    private static final String[] CN_DEC_UNITS = new String[]{"角", "分", "毫", "厘"};

    /**
     * 整数金额时后面跟的字符
     */
    private static final String CN_INTEGER = "整";

    /**
     * 整型完以后的单位
     */
    private static final String CN_INT_LAST = "圆";

    /**
     * 最大处理的数字
     */
    private static final BigDecimal MAX_NUM = new BigDecimal("999999999999999.9999");


    public static String numberMoneyToChineseMoney(BigDecimal number){
        //金额整数部分
        String integerNum;
        //金额小数部分
        String decimalNum;
        //输出的中文金额字符串
        StringBuilder chineseBuilder = new StringBuilder();
        //分离金额后用的数组，预定义
        String[] parts;

        if (number.compareTo(MAX_NUM)>0) {
            return "";
        }
        String zero = "0";
        if (number.compareTo(new BigDecimal(zero)) == 0) {
            chineseBuilder.append(CN_NUMS[0]).append(CN_INT_LAST).append(CN_INTEGER);
            return chineseBuilder.toString();
        }
        String money = number.toString();
        char point = '.';
        if (money.indexOf(point) == -1) {
            integerNum = money;
            decimalNum = "";
        } else {
            parts = money.split("\\.");
            integerNum = parts[0];
            decimalNum = parts[1];
        }
        //获取整型部分转换
        StringBuilder intNumBuilder = new StringBuilder();
        if (Integer.parseInt(integerNum) > 0) {
            int zeroCount = 0;
            int intLen = integerNum.length();
            for (int i = 0; i < intLen; i++) {
                String n = integerNum.substring(i, i+1);
                int p = intLen - i - 1;
                int q = p / 4;
                int m = p % 4;
                if ("0".equals(n)) {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        intNumBuilder.append(CN_NUMS[0]);
                    }
                    //归零
                    zeroCount = 0;
                    intNumBuilder.append(CN_NUMS[Integer.parseInt(n)]).append(CN_INT_RADICAL[m]);
                }
                if (m == 0 && zeroCount < 4) {
                    intNumBuilder.append(CN_INT_UNITS[q]);
                }
            }
            intNumBuilder.append(CN_INT_LAST);
            chineseBuilder.append(intNumBuilder);
            //整型部分处理完毕
        }
        //小数部分
        if ("".equals(decimalNum)) {
            chineseBuilder.append(CN_INTEGER);
        }else {
            int decLen = decimalNum.length();
            for (int i = 0; i < decLen; i++) {
                String n = decimalNum.substring(i, i+1);
                if (!"0".equals(n)) {
                    chineseBuilder.append(CN_NUMS[Integer.parseInt(n)]).append(CN_DEC_UNITS[i]);
                }
            }
        }
        return chineseBuilder.toString();
    }
}

