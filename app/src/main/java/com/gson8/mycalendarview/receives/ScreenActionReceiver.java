package com.gson8.mycalendarview.receives;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/9/11
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.receives.ScreenActionReceiver
 * Description: null
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gson8.mycalendarview.CalendarProvider;

public class ScreenActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setAction(CalendarProvider.ACTION_NOW_MONTH);
        context.sendBroadcast(i);
    }
}
