package com.gson8.mycalendarview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import com.gson8.mycalendarview.services.TimeService;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Toast.makeText(MainActivity.this, year + "." + month + "." + dayOfMonth,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(this, TimeService.class);
        startService(intent);
    }
}
