package com.gson8.timeservicedynamic;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/9/11
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.timeservicedynamic.TimeService
 * Description: null
 */

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TimeService extends Service {

    private TimeTickReceiver timeTickReceiver;
    private IntentFilter filter;
    private static final String TAG = TimeService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);

        timeTickReceiver = new TimeTickReceiver();
        registerReceiver(timeTickReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        unregisterReceiver(timeTickReceiver);

    }
}
