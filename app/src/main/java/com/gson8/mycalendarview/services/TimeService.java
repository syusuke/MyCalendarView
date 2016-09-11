package com.gson8.mycalendarview.services;

/*
 * MyCalendarView making by Syusuke/琴声悠扬 on 2016/9/11
 * E-Mail: Zyj7810@126.com
 * Package: com.gson8.mycalendarview.services.TimeService
 * Description: null
 */

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gson8.mycalendarview.receives.TimeTickReceiver;

public class TimeService extends Service {

    private static final String TAG = TimeService.class.getSimpleName();
    //监听时间变化的 这个receiver只能动态创建
    private TimeTickReceiver mTimeTickReceiver;
    private IntentFilter mFilter;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_TIME_TICK);
        mFilter.addAction(Intent.ACTION_TIME_CHANGED);
        mTimeTickReceiver = new TimeTickReceiver();
        registerReceiver(mTimeTickReceiver, mFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeTickReceiver);
    }
}
