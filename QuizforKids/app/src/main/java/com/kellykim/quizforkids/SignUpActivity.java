package com.kellykim.quizforkids;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText txtEmail, txtNewUsername, txtNewPassword;
    Button registerButton;
    usersDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar(); //툴바에 백버튼 생성
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDB = new usersDatabaseHelper(this, null, null, 1);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtNewUsername = (EditText)findViewById(R.id.txtNewUsername);
        txtNewPassword = (EditText)findViewById(R.id.txtNewPassword);

        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                register();
            }
        });
    }
    public void register(){
        if (txtEmail.getText().toString().isEmpty())
            message.showMessage(this, "Error", "Please enter email");
        else if (txtNewUsername.getText().toString().isEmpty())
            message.showMessage(this, "Error", "Please enter username");
        else if (txtNewPassword.getText().toString().isEmpty())
            message.showMessage(this, "Error", "Please enter password");
        else {
            boolean isInserted = myDB.insertData(txtEmail.getText().toString(),
                    txtNewUsername.getText().toString(),
                    txtNewPassword.getText().toString());
            if (isInserted == true){
                Toast.makeText(SignUpActivity.this, "Hi, " + txtNewUsername.getText().toString()
                        + "!\nYou're registered now.\nLog in and let's get started!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                message.showMessage(this, "Error", "Cannot register");
        }
    }
}