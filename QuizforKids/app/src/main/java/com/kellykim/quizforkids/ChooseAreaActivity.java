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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ChooseAreaActivity extends AppCompatActivity {
    public static final String AREA = "area";
    int user_id;
    String area;
    String[] areaArray;
    Button logoutButton, startQuizButton, previousAttemptsButton2, ChangePwButton;
    scoresDatabaseHelper scoresDB;
    usersDatabaseHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        scoresDB = new scoresDatabaseHelper(this, null, null, 1);
        userDB = new usersDatabaseHelper(this, null, null, 1);

        logoutButton = (Button)findViewById(R.id.logoutButton);
        startQuizButton = (Button)findViewById(R.id.startQuizButton);
        previousAttemptsButton2 = (Button)findViewById(R.id.previousAttemptsButton2);
        ChangePwButton = (Button)findViewById(R.id.changePwButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAreaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (area.equals("ANIMALS")) {
                    intent = new Intent(ChooseAreaActivity.this, AnimalsQ1Activity.class);
                } else{ //CARTOONS 선택
                    intent = new Intent(ChooseAreaActivity.this, CartoonsQ1Activity.class);
                }
                Toast.makeText(ChooseAreaActivity.this, area + " is selected! Good luck!", Toast.LENGTH_LONG).show();
                intent.putExtra(MainActivity.ID, user_id);
                startActivity(intent);
            }
        });

        previousAttemptsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = scoresDB.viewRecord(user_id);
                if (res.getCount() == 0){
                    message.showMessage(ChooseAreaActivity.this, "Error", "No record found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append(res.getString(2) + " area - " + "attempt started on " + res.getString(3) + " - points earned " + res.getString(4) + "\n");
                }
                message.showMessage(ChooseAreaActivity.this, "Hi " + username_from_id(user_id), buffer.toString());
            }
        });

        ChangePwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAreaActivity.this, ChangePasswordActivity.class);
                intent.putExtra(MainActivity.ID, user_id);
                startActivity(intent);
            }
        });

        //spinner
        Spinner area_spinner = findViewById(R.id.area_spinner);
        areaArray = getResources().getStringArray(R.array.array0);
        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, areaArray
        );
        area_spinner.setAdapter(adapter0);
        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Intent intent = getIntent();
        user_id = intent.getExtras().getInt(MainActivity.ID);

    }

    public String username_from_id(int id) {
        Cursor res = userDB.viewRecord_by_id(id);
        if (res.getCount() == 0) {
            message.showMessage(this, "Error", "No record found");
            return null;
        } else {
            res.moveToFirst();
            return res.getString(2);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu - adds items to the app bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();
        if (id == R.id.menu_info) {
            Intent intent = new Intent(this, InformationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}