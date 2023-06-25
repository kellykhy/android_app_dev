package com.kellykim.exhibitionguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class VisualShow extends AppCompatActivity {
    String exhibition;
    String day;
    String time;
    String[] dayArray;
    String[] timeArray2;
    int default_time;
    int default_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualshow);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        //전시회(exhibition) 잘 넘겨받았는지 점검
        Intent intent = getIntent();
        exhibition = intent.getExtras().getString(NotVisualShow.EXHIBITION);

        //현재 시간(시, 분)
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        int now_hour = now.get(Calendar.HOUR_OF_DAY);

        Date currentDate = new Date();
        now.setTime(currentDate);
        int now_day = now.get(Calendar.DAY_OF_WEEK) -1;

        // 1. 요일
        Spinner spinner_day = findViewById(R.id.spinner_day);
        dayArray = getResources().getStringArray(R.array.array0);
        default_day = now_day;
        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dayArray
        );
        spinner_day.setAdapter(adapter0);
        spinner_day.setSelection(default_day);
        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 2. 시간
        Spinner spinner_time = findViewById(R.id.spinner_time);
        timeArray2 = getResources().getStringArray(R.array.array2);

        if (now_hour ==15 || now_hour == 16)
            default_time = 1;
        else if (now_hour == 17 || now_hour == 18)
            default_time = 2;
        else
            default_time = 0;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, timeArray2
        );
        spinner_time.setAdapter(adapter);

        spinner_time.setSelection(default_time);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu - adds items to the app bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menu_select_info) {
            Intent intent1 = new Intent(this, NumberActivity.class);
            intent1.putExtra(NotVisualShow.EXHIBITION, exhibition);
            intent1.putExtra(NumberActivity.DAY, day);
            intent1.putExtra(NumberActivity.TIME, time);
            startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
