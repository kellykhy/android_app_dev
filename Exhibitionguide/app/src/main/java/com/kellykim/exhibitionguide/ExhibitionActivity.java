package com.kellykim.exhibitionguide;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;

public class ExhibitionActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    String exhibition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroup = findViewById(R.id.exhibitions_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = checkedId;
                if (id == R.id.art_gallery)
                    exhibition = "Art Gallery";
                else if (id == R.id.wwi_gallery)
                    exhibition = "WWI exhibition";
                else if (id == R.id.exploring_the_space)
                    exhibition = "Exploring the space";
                else if (id == R.id.visual_show)
                    exhibition = "Visual show";
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
            if (exhibition.equals("Visual show")) {
                Intent intent2 = new Intent(this, VisualShow.class);
                intent2.putExtra(NotVisualShow.EXHIBITION, exhibition);
                startActivity(intent2);
                return true;
            } else {
                Intent intent3 = new Intent(this, NotVisualShow.class);
                intent3.putExtra(NotVisualShow.EXHIBITION, exhibition);
                startActivity(intent3);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}