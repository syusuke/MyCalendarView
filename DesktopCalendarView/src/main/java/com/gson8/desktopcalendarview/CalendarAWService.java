package com.gson8.desktopcalendarview;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class CalendarAWService extends RemoteViewsService {

    private static final String TAG = CalendarAWService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CalendarAWRemoteViewsFactory(this, intent);
    }

    private class CalendarAWRemoteViewsFactory implements RemoteViewsFactory {

        private Context mContext;
        private int mAppWidgetId;

        private String IMAGE_ITEM = "imgage_item";
        private String TEXT_ITEM = "text_item";
        private List<DateBean> mDateData;

        public CalendarAWRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public RemoteViews getViewAt(int position) {
            DateBean bean = mDateData.get(position);


            Log.e(TAG, "getViewAt: " + position);

            // 获取 item_calendarxml 对应的RemoteViews
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_calendar);

            if(bean != null) {

                rv.setTextViewText(R.id.tv_gvitem_date, mDateData.get(position).getDay() + "");
                rv.setTextViewText(R.id.tv_gvitem_lunar, mDateData.get(position).getShowLunarDay());

//                rv.setInt(R.id.item_layout, "setBackgroundColor", Color.BLUE);

                Intent fillInIntent = new Intent();
                fillInIntent.putExtra(CalendarAWProvider.EXTRA_POSITION, position);
                fillInIntent
                        .putExtra(CalendarAWProvider.EXTRA_ITEM_FULL_LUNAR, bean.getFullLunar());
                fillInIntent.putExtra(CalendarAWProvider.EXTRA_ITEM_DAY, bean.getDay());
                fillInIntent.putExtra(CalendarAWProvider.EXTRA_ITEM_MONTH, bean.getYear());
                fillInIntent.putExtra(CalendarAWProvider.EXTRA_ITEM_YEAR, bean.getMonth());

                int it = DateUtils.isToday(bean);
                if(it != -1 && it == position) {
                    rv.setTextColor(R.id.tv_gvitem_date, Color.RED);
                    rv.setTextColor(R.id.tv_gvitem_lunar, Color.RED);
                }
                rv.addView(R.id.item_layout,
                        new RemoteViews(mContext.getPackageName(),
                                R.layout.item_loading));
                rv.setOnClickFillInIntent(R.id.item_layout, fillInIntent);
            } else {
                rv.setTextViewText(R.id.tv_gvitem_date, "");
                rv.setTextViewText(R.id.tv_gvitem_lunar, "");
            }
            return rv;
        }

        @Override
        public void onCreate() {
            mDateData = new ArrayList<>();

            update(2016, 8);
        }

        public void update(int year, int month) {
            mDateData.clear();
            int aMonthNum = DateUtils.getDaysByYearMonth(year, month);
            int weekNum = DateUtils.getWeek(year, month);

            int index = 0;

            for(int i = 0; i < aMonthNum + weekNum - 1; i++) {
                if(index < weekNum - 1) {
                    mDateData.add(null);
                    index++;
                } else {
                    int day = i - weekNum + 2;
                    mDateData.add(new DateBean(year, month, day));
                }
            }
        }


        @Override
        public int getCount() {
            // 返回“集合视图”中的数据的总数
            return mDateData == null ? 0 : mDateData.size();
        }

        @Override
        public long getItemId(int position) {
            // 返回当前项在“集合视图”中的位置
            return position;
        }

        @Override
        public RemoteViews getLoadingView() {
//            return new RemoteViews(mContext.getPackageName(), R.layout.item_loading);
            return null;
        }

        @Override
        public int getViewTypeCount() {
            // 只有一类 GridView
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onDataSetChanged() {


            Log.e(TAG, "onDataSetChanged: ");

        }

        @Override
        public void onDestroy() {
            mDateData.clear();
        }


    }
}
