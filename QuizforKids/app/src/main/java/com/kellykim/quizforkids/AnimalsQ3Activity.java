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

public class AnimalsQ3Activity extends AppCompatActivity {

    int correct_answers;
    int n3, n4;
    int user_id;
    int flag = 0;
    ImageView animal3;
    EditText txtAnimal3;
    quizDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_q3);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDB = new quizDatabaseHelper(this, null, null, 1);

        animal3 = (ImageView)findViewById(R.id.animal3);
        txtAnimal3 = (EditText)findViewById(R.id.txtAnimal3);

        Intent intent = getIntent();
        correct_answers = intent.getExtras().getInt(AnimalsQ1Activity.CORRECT_COUNT);
        user_id = intent.getExtras().getInt(MainActivity.ID);
        n3 = intent.getExtras().getInt(AnimalsQ1Activity.N3);
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
            Intent intent1 = new Intent(this, AnimalsQ4Activity.class);
            intent1.putExtra(AnimalsQ1Activity.CORRECT_COUNT, correct_answers);
            intent1.putExtra(MainActivity.ID, user_id);
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
        Cursor res = myDB.viewRecord_1(n3);
        if (res.getCount() == 0){
            message.showMessage(this, "Error", "No record found");
            return;
        }
        res.moveToFirst();
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/"+res.getString(1), null, c.getPackageName());
        animal3.setImageResource(id);
    }

    public boolean isCorrect(){
        Cursor res = myDB.viewRecord_1(n3);
        res.moveToFirst();
        if (txtAnimal3.getText().toString().equals(res.getString(1)))
            return true;
        else
            return false;
    }
}