package com.gson8.desktopcalendarview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CalendarAWProvider extends AppWidgetProvider {

    private static final String TAG = CalendarAWProvider.class.getSimpleName();

    public static final String ACTION_LUNAR = "com.gson8.desktopcalendarview.LUNAR_CLICK";
    public static final String ACTION_GRIDVIEW_ITEM = "com.gson8.desktopcalendarview.GRIDVIEW_ITEM";

    public static final String EXTRA_ITEM_FULL_LUNAR =
            "com.gson8.desktopcalendarview.EXTRA_ITEM_FULL_LUNAR";
    public static final String EXTRA_ITEM_MONTH = "com.gson8.desktopcalendarview.EXTRA_ITEM_MONTH";
    public static final String EXTRA_ITEM_YEAR = "com.gson8.desktopcalendarview.EXTRA_ITEM_YEAR";
    public static final String EXTRA_ITEM_DAY = "com.gson8.desktopcalendarview.EXTRA_ITEM_DAY";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        for(int appWidgetId : appWidgetIds) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.layout_calendar);

            //最上面农历的点击事件
            Intent btIntent = new Intent().setAction(ACTION_LUNAR);
            PendingIntent btPendingIntent = PendingIntent
                    .getBroadcast(context, 0, btIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.tv_title_lunar, btPendingIntent);

            //GridView点击事件
            Intent serviceIntent = new Intent(context, CalendarAWService.class);
            rv.setRemoteAdapter(R.id.gridview, serviceIntent);

            Intent gridIntent = new Intent();
            gridIntent.setAction(ACTION_GRIDVIEW_ITEM);
            gridIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent
                    .getBroadcast(context, 0, gridIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.gridview, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Log.e(TAG, "onReceive : " + intent.getAction());

        if(action.equals(ACTION_GRIDVIEW_ITEM)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            String fullLunar = intent.getStringExtra(EXTRA_ITEM_FULL_LUNAR);

            int day = intent.getIntExtra(EXTRA_ITEM_DAY, 1);
            int month = intent.getIntExtra(EXTRA_ITEM_MONTH, 1);
            int year = intent.getIntExtra(EXTRA_ITEM_YEAR, 1900);

            RemoteViews remoteViews =
                    new RemoteViews(context.getPackageName(), R.layout.layout_calendar);
            remoteViews.setTextViewText(R.id.tv_title_lunar, fullLunar);

            remoteViews.setTextColor(R.id.tv_gvitem_lunar, Color.RED);
            remoteViews.setTextColor(R.id.tv_gvitem_date, Color.RED);

            remoteViews.setInt(R.id.item_layout, "setBackgroundColor", Color.BLUE);

            Intent serviceIntent = new Intent(context, CalendarAWService.class);
            remoteViews.setRemoteAdapter(R.id.gridview, serviceIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        } else if(action.equals(ACTION_LUNAR)) {
            Toast.makeText(context, "Lunar Click", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
}
