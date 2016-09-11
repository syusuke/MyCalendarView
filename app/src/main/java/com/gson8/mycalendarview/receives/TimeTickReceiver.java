package com.gson8.mycalendarview.receives;

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

import com.gson8.mycalendarview.CalendarProvider;
import com.gson8.mycalendarview.Global;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTickReceiver extends BroadcastReceiver {

    private static final String TAG = TimeTickReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_TIME_TICK)) {
            //每分钟触发一次
            if(Global.DEBUG) {
                Log.e(TAG, "onReceive: 每分钟触发一次");
                Log.e(TAG, "时间变化了... " +
                        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                                .format(System.currentTimeMillis()));
            }
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

            if(sdf.format(now).contains("00000")) {
                if(Global.DEBUG)
                    Log.e(TAG, "onReceive: 下一天啦  ... ");
                Intent i = new Intent();
                i.setAction(CalendarProvider.ACTION_NEW_DAY);
                context.sendBroadcast(i);
            }

        } else {
            if(Global.DEBUG) {
                Log.e(TAG, "onReceive: ELSE----");
                Log.e(TAG, "手动改变时间 " +
                        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                                .format(System.currentTimeMillis()));
            }
            Intent i = new Intent();
            i.setAction(CalendarProvider.ACTION_HANDLE_CHANGE_DATE);
            context.sendBroadcast(i);

        }
    }
}
