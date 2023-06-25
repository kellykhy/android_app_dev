package com.kellykim.exhibitionguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;

public class ChargeActivity extends AppCompatActivity {
    public static final String NUMBER = "number";
    String[] dayArray;
    String[] exhibitionArray;
    String exhibition;
    String day;
    String time;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        dayArray = getResources().getStringArray(R.array.array0);
        exhibitionArray = getResources().getStringArray(R.array.array3);

        //전시회(exhibition), 요일(day), 시간(time) 잘 넘겨받았는지 점검
        Intent intent = getIntent();
        exhibition = intent.getExtras().getString(NotVisualShow.EXHIBITION);
        day = intent.getExtras().getString(NumberActivity.DAY);
        time = intent.getExtras().getString(NumberActivity.TIME);
        number = intent.getExtras().getInt(ChargeActivity.NUMBER);

        TextView users_choice = findViewById(R.id.display);
        users_choice.setText(getString(R.string.display_total_charge, exhibition, day, time, number, this.get_TotalAmount()) );
    }

    int get_TotalAmount() {
        int charge;
        int[] price_weekday = {25, 20, 30, 40};
        int[] price_weekend = {30, 25, 35, 40};
        int idx_day = Arrays.asList(dayArray).indexOf(day);
        int idx_exh = Arrays.asList(exhibitionArray).indexOf(exhibition);
        if (idx_day == 0 || idx_day == 6) {
            charge = this.number * price_weekend[idx_exh];
        } else {
            charge = this.number * price_weekday[idx_exh];
        }
        if (this.number >= 4)
            charge *= 0.9;
        return charge;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
                finish();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }
}