package com.gson8.mycalendarview;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/8/23
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.CalendarProvider
 * Description: null
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.gson8.mycalendarview.calutils.Lauar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarProvider extends AppWidgetProvider {
    public static final String ACTION_PRIV_MONTH = "action.PRIV";
    public static final String ACTION_NEXT_MONTH = "action.NEXT";
    public static final String ACTION_NOW_MONTH = "action.NOW";

    public static final String ACTION_NEW_DAY = "com.gson8.mycalendarview.action.new_day";
    public static final String ACTION_HANDLE_CHANGE_DATE =
            "com.gson8.mycalendarview.action.handle.date";

    public static final String YEAR_EXTRA = "year_extra";
    public static final String MONTH_EXTRA = "month_extra";

    public static final String TAG = "CalendarProvider-TEST";


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e(TAG, "添加桌面");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int appWidgetId : appWidgetIds) {
            drawWidget(context, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        if(action.equals(ACTION_PRIV_MONTH)) {
            //上一个月
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

            reDrawWidget(context);
        } else if(action.equals(ACTION_NEXT_MONTH)) {
            // 下一个月
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

            reDrawWidget(context);
        } else if(action.equals(ACTION_NOW_MONTH)) {
            //回到当前月
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().remove(YEAR_EXTRA).remove(MONTH_EXTRA).apply();

            reDrawWidget(context);
        } else if(action.equals(Intent.ACTION_TIME_TICK)) {

            Log.e("mView", "TIME: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));

        } else if(action.equals(ACTION_HANDLE_CHANGE_DATE)) {

            Log.e("mView", "ACTION_HANDLE_CHANGE_DATE: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));


            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().remove(YEAR_EXTRA).remove(MONTH_EXTRA).apply();
            reDrawWidget(context);
        } else if(action.equals(ACTION_NEW_DAY)) {
            Log.e("mView", "ACTION_NEW_DAY: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().remove(YEAR_EXTRA).remove(MONTH_EXTRA).apply();
            reDrawWidget(context);
        }


        /*else if(action.equals(Intent.ACTION_TIME_CHANGED)) {

            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().remove(YEAR_EXTRA).remove(MONTH_EXTRA).apply();


            Log.e("mView", "TIME: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));


            reDrawWidget(context);
        } else if(action.equals(Intent.ACTION_DATE_CHANGED)) {
            Log.e("mView", "DATE: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));
        } else if(action.equals(Intent.ACTION_TIME_TICK)) {
            Log.e("mView", "TICK: " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));
        }*/
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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Resources res = context.getResources();
        Bundle widgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        boolean mini = false;
        int numWeeks = 6;
        if(widgetOptions != null) {
            int minWidthDp = widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
            int minHeightDp = widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
            mini = minHeightDp <= res.getInteger(R.integer.max_height_mini_view_dp);
            if(mini) {
                numWeeks = minHeightDp <= res.getInteger(R.integer.max_height_mini_view_1_row_dp)
                        ? 1 : 2;
            }
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_YEAR);
        int todayYear = cal.get(Calendar.YEAR);
        int thisMonth;
        if(!mini) {
            thisMonth = sp.getInt(MONTH_EXTRA, cal.get(Calendar.MONTH));
            int thisYear = sp.getInt(YEAR_EXTRA, cal.get(Calendar.YEAR));
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, thisMonth);
            cal.set(Calendar.YEAR, thisYear);
        } else {
            thisMonth = cal.get(Calendar.MONTH);
        }

        rv.setTextViewText(R.id.tv_title, android.text.format.DateFormat.format("yyyy - MM", cal));
        rv.setTextViewText(R.id.tv_month, cal.get(Calendar.MONTH) + 1 + "");

        if(!mini) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            int monthStartDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, 1 - monthStartDayOfWeek);
        } else {
            int todayDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.add(Calendar.DAY_OF_MONTH, 1 - todayDayOfWeek);
        }

        rv.removeAllViews(R.id.ll_calendar_layout);

        for(int week = 0; week < numWeeks; week++) {
            RemoteViews rowRv = new RemoteViews(context.getPackageName(), R.layout.a_row_week);
            for(int day = 0; day < 7; day++) {
                boolean inMonth = cal.get(Calendar.MONTH) == thisMonth;
                boolean inYear = cal.get(Calendar.YEAR) == todayYear;
                boolean isToday = inYear && inMonth && (cal.get(Calendar.DAY_OF_YEAR) == today);

                boolean isFirstOfMonth = cal.get(Calendar.DAY_OF_MONTH) == 1;
                RemoteViews cellRv =
                        new RemoteViews(context.getPackageName(), R.layout.item_normal);
                if(inMonth) {
//                    cellLayoutResId = R.layout.cell_day_this_month;
                } else {
                    cellRv.setTextColor(R.id.tv_item_date, Color.GRAY);
                    cellRv.setTextColor(R.id.tv_item_lunar, Color.GRAY);
                }

                if(isToday) {
//                    cellRv.setInt(R.id.item_layout, "setBackgroundColor", Color.GREEN);
                    cellRv.setInt(R.id.item_layout, "setBackgroundResource", R.drawable.bg_today);
                }

                cellRv.setTextViewText(R.id.tv_item_date,
                        Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
//                cellRv.setTextViewText(R.id.tv_item_lunar, new Lunar(cal).getLunar());
                cellRv.setTextViewText(R.id.tv_item_lunar, new Lauar(cal).getMyLauar());
//                cellRv.setTextViewText(R.id.tv_item_lunar, "16/正月");

                rowRv.addView(R.id.row_week_container, cellRv);
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            rv.addView(R.id.ll_calendar_layout, rowRv);
        }

        Intent intent = new Intent(context, CalendarProvider.class).setAction(ACTION_NOW_MONTH);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.tv_title, pendingIntent);

        rv.setOnClickPendingIntent(R.id.iv_priv_month, PendingIntent.getBroadcast(context, 0,
                new Intent(context, CalendarProvider.class).setAction(ACTION_PRIV_MONTH),
                PendingIntent.FLAG_UPDATE_CURRENT));

        rv.setOnClickPendingIntent(R.id.iv_next_month, PendingIntent.getBroadcast(context, 0,
                new Intent(context, CalendarProvider.class).setAction(ACTION_NEXT_MONTH),
                PendingIntent.FLAG_UPDATE_CURRENT));

        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

}
