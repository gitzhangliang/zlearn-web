package com.zl.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangliang
 * @date 2019/12/3.
 */
@Slf4j
public class IdCardUtil {
    /**
     * 身份证号码中的出生日期的格式
     */
    private static final String BIRTH_DATE_FORMAT = "yyyyMMdd";

    @Getter
    private String errorMessage = "";
    /**
     * 身份证的最小出生日期,1900年1月1日
     */
    private static final Date MIN_BIRTH_DATE = new Date(-2209017600000L);

    /**
     * 新版身份证号码长度
     */
    private static final int NEW_CARD_NUMBER_LENGTH = 18;

    /**
     * 旧版身份证号码长度
     */
    private static final int OLD_CARD_NUMBER_LENGTH = 15;

    /**
     * 18位身份证中最后一位校验码
     */
    private static final char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private static final int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 当前时间
     */
    private Date currentDate = new Date();

    private final String cardNumber;


    public IdCardUtil(String idCard) {
        cardNumber = length15To18(idCard);
    }

    /**身份证号码校验
     */
    public boolean validate() {
        boolean result = true;
        try {
            if (StringUtils.isBlank(cardNumber)) {
                errorMessage = "身份证不能为空";
                log.error(errorMessage);
                return false;
            }
            if (cardNumber.length() != NEW_CARD_NUMBER_LENGTH) {
                errorMessage = "身份证号长度有误，应该为15位或18位";
                log.error(errorMessage);
                return false;
            }
            char ch;
            for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
                ch = cardNumber.charAt(i);
                result = ch >= '0' && ch <= '9';
                if (!result) {
                    break;
                }
            }
            if (!result) {
                errorMessage = "身份证号的前17位必须是阿拉伯数字";
                log.error(errorMessage);
                return false;
            }
            char c18 = calculateVerifyCode(cardNumber);
            result = c18 == cardNumber.charAt(NEW_CARD_NUMBER_LENGTH - 1);
            if (!result) {
                errorMessage = "身份证号的第18位不正确,应该是"+c18;
                log.error(errorMessage);
                return false;
            }
            Date birthDate = getBirthDate();
            if (null == birthDate) {
                return false;
            }
            result = birthDate.before(currentDate);
            if (!result) {
                errorMessage = "身份证出生日期不能晚于当前时间";
                log.error(errorMessage);
                return false;
            }
            result = birthDate.after(MIN_BIRTH_DATE);
            if (!result) {
                errorMessage = "身份证的最小出生日期是1900年1月1日";
                log.error(errorMessage);
                return false;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**校验码（第十八位数）
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值
     * Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 计算模 Y = mod(S, 11)
     * 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */
    private char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        char ch;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            ch = cardNumber.charAt(i);
            sum += (ch - '0') * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**获取身份证号码中的生日
     */
    public Date getBirthDate() {
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        try {
            return new SimpleDateFormat(BIRTH_DATE_FORMAT).parse(getBirthDayPart());
        } catch (ParseException e) {
            return null;
        }
    }

    private String getBirthDayPart() {
        return cardNumber.substring(6, 14);
    }

    /**
     * 15位身份证号码与18位身份证号码的区别为：
     * 1: 15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪;
     * 2: 15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     */
    private String length15To18(String cardNumber) {
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        cardNumber = cardNumber.trim();
        if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()) {
            StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
            buf.append(cardNumber, 0, 6);
            buf.append("19");
            buf.append(cardNumber.substring(6));
            buf.append(calculateVerifyCode(buf));
            return buf.toString();
        }
        return cardNumber;
    }


    /**获取身份证的第17位，奇数为男性，偶数为女性
     */
    private int getGenderCode() {
        char genderCode = cardNumber.charAt(NEW_CARD_NUMBER_LENGTH - 2);
        return (genderCode - '0') & 0x1;
    }

    /**判断是否为男性
     */
    public boolean isMale() {
        if (StringUtils.isBlank(cardNumber)) {
            return false;
        }
        return 1 == getGenderCode();
    }

    /**判断是否为女性
     */
    public boolean isFemal() {
        if (StringUtils.isBlank(cardNumber)) {
            return false;
        }
        return 1 != getGenderCode();
    }

    /**功能：设置地区编码
     */
    private Map<String, String> getAreaCode() {
        Map<String, String> map = new HashMap<>(50);
        map.put("11", "北京");
        map.put("12", "天津");
        map.put("13", "河北");
        map.put("14", "山西");
        map.put("15", "内蒙古");
        map.put("21", "辽宁");
        map.put("22", "吉林");
        map.put("23", "黑龙江");
        map.put("31", "上海");
        map.put("32", "江苏");
        map.put("33", "浙江");
        map.put("34", "安徽");
        map.put("35", "福建");
        map.put("36", "江西");
        map.put("37", "山东");
        map.put("41", "河南");
        map.put("42", "湖北");
        map.put("43", "湖南");
        map.put("44", "广东");
        map.put("45", "广西");
        map.put("46", "海南");
        map.put("50", "重庆");
        map.put("51", "四川");
        map.put("52", "贵州");
        map.put("53", "云南");
        map.put("54", "西藏");
        map.put("61", "陕西");
        map.put("62", "甘肃");
        map.put("63", "青海");
        map.put("64", "宁夏");
        map.put("65", "新疆");
        map.put("71", "台湾");
        map.put("81", "香港");
        map.put("82", "澳门");
        map.put("91", "国外");
        return map;
    }

    /**获取身份证号码中的地址编码
     */
    public String getAddressCode() {
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        return  getAreaCode().get(cardNumber.substring(0, 2));
    }

    public Integer getAge(){
        if (StringUtils.isBlank(cardNumber)) {
            return null;
        }
        int birthDateYear = DateUtil.getYears(getBirthDate());
        int nowYear = DateUtil.getYears(currentDate);
        return nowYear -birthDateYear;
    }
}
