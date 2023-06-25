package com.kellykim.quizforkids;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AnimalsQ1Activity extends AppCompatActivity {
    public static final String N2 = "n2";
    public static final String N3 = "n3";
    public static final String N4 = "n4";
    public static final String CORRECT_COUNT = "number of correct answers";
    int correct_answers;
    int user_id;
    int flag = 0;
    int n1, n2, n3, n4;
    ImageView animal1;
    EditText txtAnimal1;
    quizDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_q1);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        correct_answers = 0;
        myDB = new quizDatabaseHelper(this, null, null, 1);

        animal1 = (ImageView)findViewById(R.id.animal1);
        txtAnimal1 = (EditText)findViewById(R.id.txtAnimal1);

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
            Intent intent1 = new Intent(this, AnimalsQ2Activity.class);
            intent1.putExtra(CORRECT_COUNT, correct_answers);
            intent1.putExtra(MainActivity.ID, user_id);
            intent1.putExtra(N2, n2);
            intent1.putExtra(N3, n3);
            intent1.putExtra(N4, n4);
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
        Cursor res = myDB.viewRecord_1(n1);
        if (res.getCount() == 0){
            message.showMessage(this, "Error", "No record found");
            return;
        }
        res.moveToFirst();
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+res.getString(1), null, c.getPackageName());
        animal1.setImageResource(id);
    }

    public boolean isCorrect(){
        Cursor res = myDB.viewRecord_1(n1);
        res.moveToFirst();
        if (txtAnimal1.getText().toString().equals(res.getString(1)))
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
        List<Integer> random_list = new ArrayList<>(random);
        n1 = random_list.get(0).intValue() + 1;
        n2 = random_list.get(1).intValue() + 1;
        n3 = random_list.get(2).intValue() + 1;
        n4 = random_list.get(3).intValue() + 1;
    }
}