package com.gson8.timeservicedynamic;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/9/11
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.receives.TimeTickReceiver
 * Description: null
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;

public class TimeTickReceiver extends BroadcastReceiver {

    private static final String TAG = TimeTickReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if(action.equals(Intent.ACTION_TIME_TICK)) {
            //每分钟触发一次
            Log.e(TAG,
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));

        } else if(action.equals(Intent.ACTION_DATE_CHANGED)) {
            Log.e(TAG, "日期.. " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));

        } else if(action.equals(Intent.ACTION_TIME_CHANGED)) {
            //手动改时间,日期都会在这里发生.
            Log.e(TAG, "时间.. " +
                    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(System.currentTimeMillis()));

        }
    }
}
