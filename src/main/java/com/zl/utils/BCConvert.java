package com.zl.utils;

/**
 * @author tzxx
 */
public class BCConvert {
    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     * 半角!
     */
    private static final char DBC_CHAR_START = 33;

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     * 半角~
     */
    private static final char DBC_CHAR_END = 126;

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     * 全角！
     */
    private static final char SBC_CHAR_START = 65281;

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     * 全角～
     */
    private static final char SBC_CHAR_END = 65374;

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     * 全角半角转换间隔
     */
    private static final int CONVERT_STEP = 65248;

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     * 全角空格 12288
     */
    private static final char SBC_SPACE = 12288;

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     * 半角空格
     */
    private static final char DBC_SPACE = ' ';


    /**
     * <PRE>
     * 半角字符->全角字符转换
     * 只处理空格，!到˜之间的字符，忽略其他
     * </PRE>
     */
    public static String bj2qj(String src) {
        if (src == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (char aCa : ca) {
            // 如果是半角空格，直接用全角空格替代
            if (aCa == DBC_SPACE) {
                buf.append(SBC_SPACE);
            } else if ((aCa >= DBC_CHAR_START) && (aCa <= DBC_CHAR_END)) {
                // 字符是!到~之间的可见字符
                buf.append(( char ) (aCa + CONVERT_STEP));
            } else {
                // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(aCa);
            }
        }
        return buf.toString();
    }

    /**
     * <PRE>
     * 全角字符->半角字符转换
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     * </PRE>
     */
    public static String qj2bj(String src) {
        if (src == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            // 如果位于全角！到全角～区间内
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) {
                buf.append((char) (ca[i] - CONVERT_STEP));
                // 如果是全角空格
            } else if (ca[i] == SBC_SPACE) {
                buf.append(DBC_SPACE);
                // 不处理全角空格，全角！到全角～区间外的字符
            } else {
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }
}