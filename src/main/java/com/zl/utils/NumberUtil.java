package com.zl.utils;

/**
 * @author zhangliang
 * @date 2019/12/22.
 */
public class NumberUtil {

    /**
     * 汉字的数字
     */
    private static final String[] NUMS = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 基本单位
     */
    private static final String[] CN_INT_RADICAL = new String[]{"", "十", "百", "千"};
    /**
     * 对应整数部分扩展单位
     */
    private static final String[] CN_INT_UNITS = new String[]{"", "万", "亿", "兆"};

    private static final String TEN = "一十";
    private NumberUtil(){}

    public static String numberToChinese(int number){
        String integerNum;
        StringBuilder chineseBuilder = new StringBuilder();
        if (number ==  0) {
            chineseBuilder.append(NUMS[0]);
            return chineseBuilder.toString();
        }
        integerNum = number+"";

        StringBuilder builder = new StringBuilder();
        if (number > 0) {
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
                        builder.append(NUMS[0]);
                    }
                    zeroCount = 0;
                    builder.append(NUMS[Integer.parseInt(n)]).append(CN_INT_RADICAL[m]);
                }
                if (m == 0 && zeroCount < 4) {
                    builder.append(CN_INT_UNITS[q]);
                }
            }
            chineseBuilder.append(builder);
        }
        String s = chineseBuilder.toString();
        if (s.startsWith(TEN)) {
            return s.substring(1);
        }
        return chineseBuilder.toString();
    }
}
