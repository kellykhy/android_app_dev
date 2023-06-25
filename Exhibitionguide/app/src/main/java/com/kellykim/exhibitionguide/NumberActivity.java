package com.kellykim.exhibitionguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class NumberActivity extends AppCompatActivity {

    public static final String DAY = "day";
    public static final String TIME = "time";
    String exhibition;
    String day;
    String time;
    int number;
    EditText visitor_number;
    TextView tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_acitivity);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        visitor_number = findViewById(R.id.editText_num_visitors);

        //전시회(exhibition), 요일(day), 시간(time) 잘 넘겨받았는지 점검
        Intent intent = getIntent();
        exhibition = intent.getExtras().getString(NotVisualShow.EXHIBITION);
        day = intent.getExtras().getString(DAY);
        time = intent.getExtras().getString(TIME);
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
        if (id == R.id.menu_select_info){
            number = Integer.parseInt(visitor_number.getText().toString());
            Intent intent = new Intent(this, ChargeActivity.class);
            intent.putExtra(NotVisualShow.EXHIBITION, exhibition);
            intent.putExtra(DAY, day);
            intent.putExtra(TIME, time);
            intent.putExtra(ChargeActivity.NUMBER, number);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}