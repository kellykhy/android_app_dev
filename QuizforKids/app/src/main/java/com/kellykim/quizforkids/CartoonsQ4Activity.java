package com.kellykim.quizforkids;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CartoonsQ4Activity extends AppCompatActivity {
    String area = "CARTOONS";
    int user_id;
    int n4;
    int correct_answers;
    int flag = 0;
    int user_answer = 1;
    TextView Cartoons4Q;
    RadioGroup radioGroupCartoons;
    quizDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoons_q4);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroupCartoons = findViewById(R.id.radioGroupCartoons);
        radioGroupCartoons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = checkedId;
                if (id == R.id.cartoons1Button)
                    user_answer = 1;
                else if (id == R.id.cartoons2Button)
                    user_answer = 2;
                else if (id == R.id.cartoons3Button)
                    user_answer = 3;
            }
        });
        Cartoons4Q = (TextView) findViewById(R.id.Cartoons4Q);
        myDB = new quizDatabaseHelper(this, null, null, 1);

        Intent intent = getIntent();
        user_id = intent.getExtras().getInt(MainActivity.ID);
        correct_answers = intent.getExtras().getInt(AnimalsQ1Activity.CORRECT_COUNT);
        n4 = intent.getExtras().getInt(AnimalsQ1Activity.N4);

        setQuestion();
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
            if (isCorrect() && flag == 0){
                correct_answers++;
                flag = 1;
            }
            Intent intent1 = new Intent(this, QuizResultActivity.class);
            intent1.putExtra(ChooseAreaActivity.AREA, area);
            intent1.putExtra(AnimalsQ1Activity.CORRECT_COUNT, correct_answers);
            intent1.putExtra(MainActivity.ID, user_id);
            startActivity(intent1);
            return true;
        }
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setQuestion(){
        Cursor res = myDB.viewRecord_2(n4);
        if (res.getCount() == 0){
            message.showMessage(this, "Error", "No record found");
            return;
        }
        res.moveToFirst();
        Cartoons4Q.setText(res.getString(1));
        for (int i = 0; i < radioGroupCartoons .getChildCount(); i++) {
            ((RadioButton) radioGroupCartoons.getChildAt(i)).setText(res.getString(i+2));
        }
    }

    public boolean isCorrect(){
        Cursor res = myDB.viewRecord_2(n4);
        res.moveToFirst();
        if (user_answer == res.getInt(5))
            return true;
        else
            return false;
    }
}