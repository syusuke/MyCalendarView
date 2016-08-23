package com.gson8.mycalendarview;

/*
 * CanlendarViews making by Syusuke/琴声悠扬 on 2016/8/5
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.canlendarviews.DateUtils
 * Description: null
 */

import java.util.Calendar;

public class DateUtils {

    public static Calendar composeDate2Calendar(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_MONTH, day);
        return date;
    }

    public static DateBean getTodayBean() {
        Calendar c = Calendar.getInstance();
        return new DateBean(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH));
    }


    /***
     * 判断是否是今天
     *
     * @param year
     * @param month
     * @return -1 ,不在这个月,其他的数字,表示是这个月的多少号
     */
    public static int isToday(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        if(y == year) {
            int m = calendar.get(Calendar.MONTH) + 1;
            if(m == month) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return day;
            }
        }
        return -1;
    }


    /***
     * 判断是否是今天
     *
     * @param bean
     * @return -1 ,不在这个月,其他的数字,表示是这个月的多少号
     */
    public static int isToday(DateBean bean) {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        if(y == bean.getYear()) {
            int m = calendar.get(Calendar.MONTH) + 1;
            if(m == bean.getMonth()) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return day;
            }
        }
        return -1;
    }


    /**
     * 获取那个月一共有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取每月的第一天是星期几
     *
     * @param year
     * @param month
     * @return 1~7 分别对应 日,一,二,三,四,五,六 . 对应
     */
    public static int getWeek(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }


}
