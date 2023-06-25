package com.kellykim.quizforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String ID = "id";
    EditText txtUsername, txtPassword;
    Button signInButton, signUpButton;
    usersDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        myDB = new usersDatabaseHelper(this, null, null, 1);

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        signInButton = (Button)findViewById(R.id.signInButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        signInButton.setOnClickListener(clickListener);
        signUpButton.setOnClickListener(clickListener);
    }

    private final View.OnClickListener clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.signInButton:
                            checkUser();
                            break;
                        case R.id.signUpButton:
                            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
            };

    public void checkUser(){
        if (txtUsername.getText().toString().isEmpty())
            message.showMessage(this, "Error", "Please enter username");
        else if (txtPassword.getText().toString().isEmpty())
            message.showMessage(this, "Error", "Please enter password");
        else {
            Cursor res = myDB.viewRecord(txtUsername.getText().toString());
            if (res.getCount() == 0){
                message.showMessage(this, "Error", "User not found");
                return;
            }
            res.moveToFirst();
            if (txtPassword.getText().toString().equals(res.getString(3))) {
                Toast.makeText(MainActivity.this, "Welcome " + txtUsername.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra(MainActivity.ID, res.getInt(0));
                startActivity(intent);
            }
            else
                message.showMessage(this, "Error", "Check your username and password");
        }
    }
}