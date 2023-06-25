package com.kellykim.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        else if (id == R.id.menu_select_exhibition) {
            Intent intent_1;
            intent_1 = new Intent(this, ExhibitionActivity.class);
            startActivity(intent_1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}