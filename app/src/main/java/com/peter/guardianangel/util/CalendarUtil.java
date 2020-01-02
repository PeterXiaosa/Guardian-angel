package com.peter.guardianangel.util;


import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtil {


    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int getWeekNoFormat(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static Calendar toDate(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
            return c;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 参数格式：2012-12-1
     * return 12月1日
     *
     * @param date
     */
    public static String FormatDateMD(String date) {
        if (TextUtils.isEmpty(date)) {
            new Throwable();
        }
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return month + "月" + day + "日";
    }

    /**
     * 参数格式：2012-12-1
     * return 2012年12月1日
     *
     * @param date
     */
    public static String FormatDateYMD(String date) {
        if (TextUtils.isEmpty(date)) {
            new Throwable();
        }
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 参数格式：2012-12-1
     * return 2012-12-01
     *
     * @param date
     */
    public static String FormatBillDateYMD(String date) {
        if (TextUtils.isEmpty(date)) {
            new Throwable();
        }
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return year + "-" + dealMonthOrDay(Integer.parseInt(month)) + "-" + dealMonthOrDay(Integer.parseInt(day));
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate){
        long between_days = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days=(time2-time1)/(1000*3600*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    @SuppressLint("SimpleDateFormat")
    public static String getWeekByFormat(String pTime) {
        String week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            week += "六";
        }
        return week;
    }

    public static String dealMonthOrDay(int time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return "" + time;
        }
    }
}
