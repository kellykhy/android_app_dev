package com.kellykim.quizforkids;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    usersDatabaseHelper userDB;
    int user_id;
    EditText txtPreviousPw, txtNewPw;
    Button ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        userDB = new usersDatabaseHelper(this, null, null, 1);
        txtPreviousPw = (EditText)findViewById(R.id.txtPreviousPw);
        txtNewPw = (EditText)findViewById(R.id.txtNewPw);
        ConfirmButton = (Button)findViewById(R.id.ConfirmButton);
        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUser()) {
                    Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        Intent intent = getIntent();
        user_id = intent.getExtras().getInt(MainActivity.ID);
    }
    public boolean checkUser(){
        if (txtPreviousPw.getText().toString().isEmpty()) {
            message.showMessage(this, "Error", "Please enter previous password");
            return false;
        }
        else if (txtNewPw.getText().toString().isEmpty()) {
            message.showMessage(this, "Error", "Please enter new password");
            return false;
        }
        else {
            Cursor res = userDB.viewRecord_by_id(user_id);
            if (res.getCount() == 0){
                message.showMessage(ChangePasswordActivity.this, "Error", "No record found");
                return false;
            }
            res.moveToFirst();
            if ((txtPreviousPw.getText().toString()).equals(res.getString(3))) {
                Toast.makeText(ChangePasswordActivity.this, "Successfully changed password.\nSign in with the new password.", Toast.LENGTH_LONG).show();
                userDB.update_pw(txtNewPw.getText().toString(), Integer.toString(user_id));
                return true;
            }
            else {
                message.showMessage(this, "Error", "Check your previous password");
                return false;
            }
        }
    }
}