package com.gson8.desktopcalendarview;

/*
 * CanlendarViews making by Syusuke/琴声悠扬 on 2016/8/4
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.canlendarviews.DateBean
 * Description: null
 */

public class DateBean {
    private int year;
    private int month;
    private int day;
    private String showLunarDay;
    private String fullLunar;
    private boolean isClick = false;

    public DateBean() {
    }

    public DateBean(int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;
        Lunar lunar = new Lunar(DateUtils.composeDate2Calendar(year, month, day));
        this.showLunarDay = lunar.getLunar();
        fullLunar = lunar.toString();
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getShowLunarDay() {
        return showLunarDay;
    }

    public void setShowLunarDay(String showLunarDay) {
        this.showLunarDay = showLunarDay;
    }

    public void setFullLunar(String fullLunar) {
        this.fullLunar = fullLunar;
    }

    public String getFullLunar() {
        return fullLunar;
    }

    @Override
    public String toString() {
        return "" + year + "年" + month + "月" + day + " 农历:" + fullLunar + "," + showLunarDay + "," +
                isClick;
    }
}
