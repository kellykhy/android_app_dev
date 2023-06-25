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
import android.widget.Toast;

public class AnimalsQ4Activity extends AppCompatActivity {

    String area = "ANIMALS";
    int correct_answers;
    int n4;
    int user_id;
    int flag = 0;
    ImageView animal4;
    EditText txtAnimal4;
    quizDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_q4);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDB = new quizDatabaseHelper(this, null, null, 1);

        animal4 = (ImageView)findViewById(R.id.animal4);
        txtAnimal4 = (EditText)findViewById(R.id.txtAnimal4);

        Intent intent = getIntent();
        correct_answers = intent.getExtras().getInt(AnimalsQ1Activity.CORRECT_COUNT);
        user_id = intent.getExtras().getInt(MainActivity.ID);
        n4 = intent.getExtras().getInt(AnimalsQ1Activity.N4);

        setQuestion();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu - adds items to the app bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    public void setQuestion(){
        Cursor res = myDB.viewRecord_1(n4);
        if (res.getCount() == 0){
            message.showMessage(this, "Error", "No record found");
            return;
        }
        res.moveToFirst();
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+res.getString(1), null, c.getPackageName());
        animal4.setImageResource(id);
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

    public boolean isCorrect(){
        Cursor res = myDB.viewRecord_1(n4);
        res.moveToFirst();
        if (txtAnimal4.getText().toString().equals(res.getString(1)))
            return true;
        else
            return false;
    }
}