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
import android.view.TextureView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CartoonsQ1Activity extends AppCompatActivity {
    int user_id;
    int n1, n2, n3, n4;
    int correct_answers;
    int flag = 0;
    int user_answer = 1;
    TextView Cartoons1Q;
    RadioGroup radioGroupCartoons;
    quizDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoons_q1);

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

        Cartoons1Q = (TextView) findViewById(R.id.Cartoons1Q);
        correct_answers = 0; //맞춘 문제 개수
        myDB = new quizDatabaseHelper(this, null, null, 1);

        Intent intent = getIntent();
        user_id = intent.getExtras().getInt(MainActivity.ID);

        set_random_num();
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
            Intent intent1 = new Intent(this, CartoonsQ2Activity.class);
            intent1.putExtra(AnimalsQ1Activity.CORRECT_COUNT, correct_answers);
            intent1.putExtra(MainActivity.ID, user_id);
            intent1.putExtra(AnimalsQ1Activity.N2, n2);
            intent1.putExtra(AnimalsQ1Activity.N3, n3);
            intent1.putExtra(AnimalsQ1Activity.N4, n4);
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
        Cursor res = myDB.viewRecord_2(n1);
        if (res.getCount() == 0){
            message.showMessage(this, "Error", "No record found");
            return;
        }
        res.moveToFirst();
        Cartoons1Q.setText(res.getString(1));
        for (int i = 0; i < radioGroupCartoons .getChildCount(); i++) {
            ((RadioButton) radioGroupCartoons.getChildAt(i)).setText(res.getString(i+2));
        }
    }

    public boolean isCorrect(){
        Cursor res = myDB.viewRecord_2(n1);
        res.moveToFirst();
        Toast.makeText(CartoonsQ1Activity.this, Integer.toString(res.getInt(5)), Toast.LENGTH_LONG).show();
        if (user_answer == res.getInt(5))
            return true;
        else
            return false;
    }

    public void set_random_num(){
        Set<Integer> random = new HashSet<Integer>();
        Random rand = new Random();
        do{
            int int_random = rand.nextInt(10);
            random.add(int_random);
        }while (random.size() < 4);
        Iterator value = random.iterator();
        List<Integer> random_list = new ArrayList<>(random);
        n1 = random_list.get(0).intValue() + 1;
        n2 = random_list.get(1).intValue() + 1;
        n3 = random_list.get(2).intValue() + 1;
        n4 = random_list.get(3).intValue() + 1;
    }
}