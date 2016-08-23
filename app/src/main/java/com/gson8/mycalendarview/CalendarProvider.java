package com.gson8.mycalendarview;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/8/23
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.CalendarProvider
 * Description: null
 */

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarProvider extends AppWidgetProvider {
    public static final String ACTION_PRIV_MONTH = "action.PRIV";
    public static final String ACTION_NEXT_MONTH = "action.NEXT";
    public static final String ACTION_NOW_MONTH = "action.NOW";

    public static final String YEAR_EXTRA = "year_extra";
    public static final String MONTH_EXTRA = "month_extra";
    public static final String DAY_EXTRA = "day_extra";
    public static final String LUNAR_EXTRA = "lunar_extra";

    public static final String TAG = "CalendarProvider-TEST";

    private List<DateBean> mLists;

    public CalendarProvider() {
        mLists = new ArrayList<>();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate: " + SystemClock.currentThreadTimeMillis());

        for(int appWidgetId : appWidgetIds) {
            addDateData(context);
            drawWidget(context, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        Log.e(TAG, "onReceive: " + action);

        if(action.equals(ACTION_PRIV_MONTH)) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Calendar calendar = Calendar.getInstance();

            int curYear = sp.getInt(YEAR_EXTRA, calendar.get(Calendar.YEAR));
            int curMonth = sp.getInt(MONTH_EXTRA, calendar.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, curMonth);
            calendar.set(Calendar.YEAR, curYear);

            calendar.add(Calendar.MONTH, -1);       //上一个月

            sp.edit().putInt(YEAR_EXTRA, calendar.get(Calendar.YEAR))
                    .putInt(MONTH_EXTRA, calendar.get(Calendar.MONTH))
                    .apply();
            addDateData(context);
            reDrawWidget(context);
        } else if(action.equals(ACTION_NEXT_MONTH)) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Calendar calendar = Calendar.getInstance();
            int curYear = sp.getInt(YEAR_EXTRA, calendar.get(Calendar.YEAR));
            int curMonth = sp.getInt(MONTH_EXTRA, calendar.get(Calendar.MONTH));

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, curMonth);
            calendar.set(Calendar.YEAR, curYear);

            calendar.add(Calendar.MONTH, 1);
            sp.edit().putInt(YEAR_EXTRA, calendar.get(Calendar.YEAR))
                    .putInt(MONTH_EXTRA, calendar.get(Calendar.MONTH))
                    .apply();
            addDateData(context);
            reDrawWidget(context);
        } else if(action.equals(ACTION_NOW_MONTH)) {
            //回到当前月
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().remove(YEAR_EXTRA).remove(MONTH_EXTRA).apply();
            addDateData(context);
            reDrawWidget(context);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        drawWidget(context, appWidgetId);
    }


    private void reDrawWidget(Context context) {
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(
                new ComponentName(context, CalendarProvider.class));
        for(int appWidgetId : appWidgetIds) {
            drawWidget(context, appWidgetId);
        }
    }

    private void drawWidget(Context context, int appWidgetId) {

        Log.e(TAG, "drawWidget: " + System.currentTimeMillis());

        AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        DateBean db = DateUtils.getTodayBean();

        rv.setTextViewText(R.id.tv_title_lunar, db.getFullLunar()); //农历标题
        rv.setInt(R.id.ll_week_title, "setVisibility", View.VISIBLE);   //日~ 六
        rv.setTextViewText(R.id.tv_month, db.getMonth() + "");

        rv.removeAllViews(R.id.ll_calendar_layout);

        Log.e("TAGGGGG",
                "drawWidget: For Loop start" + mLists.size() + "sys" + System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        for(int i = 0, size = mLists.size() / 6; i < size; i++) {

            RemoteViews rowRv = new RemoteViews(context.getPackageName(), R.layout.a_row_week);

            int jS = (mLists.size() - i * 7 >= 6 ? 7 : mLists.size() - i * 7);
            Log.e("测试", "drawWidget: Js= " + jS);
            for(int j = 0; j < jS; j++) {

                int index = i * 7 + j;
                Log.e("测试11",
                        (i * 7 + j) + "|||" + "i=" + i + "--j=" + j + "..." + mLists.size() + "," +
                                index);
                DateBean b = mLists.get(index);

                RemoteViews itemRv =
                        new RemoteViews(context.getPackageName(), R.layout.item_normal);
                if(b == null) {
                    itemRv.setTextViewText(R.id.tv_item_date, "");
                    itemRv.setTextViewText(R.id.tv_item_lunar, "");
                } else {
                    boolean inMonth = (1 + calendar.get(Calendar.MONTH)) == b.getMonth();
                    boolean inYear = calendar.get(Calendar.YEAR) == b.getYear();

                    boolean isToday =
                            inYear && inMonth &&
                                    (calendar.get(Calendar.DAY_OF_MONTH) == b.getDay() + 2 -
                                            DateUtils.getWeek(b.getYear(), b.getMonth()));
                    Log.e(TAG, "是不是今天: " + inMonth + "," + inYear + "," + isToday);
                    Log.e(TAG, "是不是今天: " + b.getMonth() + "," + b.getYear() + "," + b.getDay());

                    if(isToday) {
//                        itemRv.setInt(R.id.item_layout, "setBackgroundColor", Color.RED);
                        itemRv.setInt(R.id.item_layout, "setBackgroundResource",
                                R.drawable.bg_today);
                        Log.e(TAG, "drawWidget: 今天");
                        //setBackgroundColor
                        //setBackgroundResource
                    }
                    itemRv.setTextViewText(R.id.tv_item_date, b.getDay() + "");
                    itemRv.setTextViewText(R.id.tv_item_lunar, b.getShowLunarDay());
                }
                rowRv.addView(R.id.row_week_container, itemRv);
            }
            rv.addView(R.id.ll_calendar_layout, rowRv);
        }

        mAppWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private void addDateData(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Calendar calendar = Calendar.getInstance();

        int year = sp.getInt(YEAR_EXTRA, calendar.get(Calendar.YEAR));
        int month = 1 + sp.getInt(MONTH_EXTRA, calendar.get(Calendar.MONTH));

        Log.e(TAG, "addDateData: " + year + ".." + month);

        mLists.clear();
        int aMonthNum = DateUtils.getDaysByYearMonth(year, month);
        int weekNum = DateUtils.getWeek(year, month);

        int index = 0;

        for(int i = 0; i < aMonthNum + weekNum - 1; i++) {
            if(index < weekNum - 1) {
                mLists.add(null);
                index++;
            } else {
                int day = i - weekNum + 2;
                mLists.add(new DateBean(year, month, day));
            }
        }

        while(mLists.size() < 36) {
            mLists.add(null);
        }
    }


}
