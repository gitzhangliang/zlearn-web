package com.zl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * @author tzxx
 */
public class DateUtil {

    public static final String FORMAT = "yyyyMMdd";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";



    /**
     * 日期格式化
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        return date != null ? new SimpleDateFormat(pattern).format(date) : null;
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }
    /**
     * 在当前日期上增加1S
     *
     * @return
     */
    public static Date addOneSecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, 1);
        return cal.getTime();
    }


    /**
     * 获取时间戳
     */
    public static String getTimeString(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static int getYears(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getYear();
    }

    public static int getMonth(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getMonthValue();
    }

    public static int getDayOfMonth(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getDayOfMonth();
    }

    public static int getDayOfYear(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getDayOfYear();
    }

    public static int getHour(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getHour();
    }

    public static int getMinite(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.getMinute();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return getYears(date1) == getYears(date1) && getMonth(date1) == getMonth(date2) && getDayOfMonth(date1) == getDayOfMonth(date2);
    }
}