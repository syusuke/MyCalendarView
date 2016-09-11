package com.gson8.mycalendarview.receives;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/9/11
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.receives.BootedReceiver
 * Description: null
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gson8.mycalendarview.services.TimeService;

public class BootedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, TimeService.class));
    }
}
