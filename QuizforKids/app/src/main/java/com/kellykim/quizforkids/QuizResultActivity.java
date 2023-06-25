package com.kellykim.quizforkids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizResultActivity extends AppCompatActivity {
    String area;
    int correct_answers;
    int user_id;
    int score;
    int overall_score;
    Button sameAreaButton, diffAreaButton, logoutButton2, previousAttemptsButton;
    usersDatabaseHelper myDB;
    scoresDatabaseHelper scoresDB;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Toolbar toolbar = findViewById(R.id.toolbar); //툴바 생성
        setSupportActionBar(toolbar);

        ImageView thumbs_up = findViewById(R.id.thumbs_up);
        Animation myanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        thumbs_up.startAnimation(myanim);

        myDB = new usersDatabaseHelper(this, null, null, 1);
        scoresDB = new scoresDatabaseHelper(this, null, null, 1);
        sameAreaButton = (Button) findViewById(R.id.sameAreaButton);
        diffAreaButton = (Button) findViewById(R.id.diffAreaButton);
        logoutButton2 = (Button) findViewById(R.id.logoutButton2);
        previousAttemptsButton = (Button) findViewById(R.id.previousAttemptsButton);

        sameAreaButton.setOnClickListener(clickListener);
        diffAreaButton.setOnClickListener(clickListener);
        logoutButton2.setOnClickListener(clickListener);
        previousAttemptsButton.setOnClickListener(clickListener);

        TextView QuizResult = (TextView) findViewById(R.id.QuizResult);

        //값 넘겨받기
        Intent intent = getIntent();
        area = intent.getExtras().getString(ChooseAreaActivity.AREA);
        correct_answers = intent.getExtras().getInt(AnimalsQ1Activity.CORRECT_COUNT);
        user_id = intent.getExtras().getInt(MainActivity.ID);

        score = 3 * correct_answers - (4-correct_answers);
        addRecord();
        overall_score();
        QuizResult.setText("Well done " + username_from_id(user_id) + ",\n" + "you have finished the " + area +
                " quiz with " + correct_answers + " correct and " + (4 - correct_answers) + " incorrect answers. " +
                "You got " + score + " points for this attempt.\n" +
                "Overall you have " + overall_score + ".");
    }

    private final View.OnClickListener clickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.sameAreaButton:
                            Intent intent1;
                            if (area.equals("ANIMALS"))
                                intent1 = new Intent(QuizResultActivity.this, AnimalsQ1Activity.class);
                            else
                                intent1 = new Intent(QuizResultActivity.this, CartoonsQ1Activity.class);
                            intent1.putExtra(MainActivity.ID, user_id);
                            startActivity(intent1);
                            break;
                        case R.id.diffAreaButton:
                            Intent intent2;
                            if (area.equals("ANIMALS"))
                                intent2 = new Intent(QuizResultActivity.this, CartoonsQ1Activity.class);
                            else
                                intent2 = new Intent(QuizResultActivity.this, AnimalsQ1Activity.class);
                            intent2.putExtra(MainActivity.ID, user_id);
                            startActivity(intent2);
                            break;
                        case R.id.logoutButton2:
                            Toast.makeText(QuizResultActivity.this,
                                    username_from_id(user_id) + ", you have overall " + overall_score + " points.", Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(QuizResultActivity.this, MainActivity.class);
                            startActivity(intent3);
                            break;
                        case R.id.previousAttemptsButton:
                            Cursor res = scoresDB.viewRecord(user_id);
                            if (res.getCount() == 0){
                                message.showMessage(QuizResultActivity.this, "Error", "No record found");
                                return;
                            }
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("You have earned " + overall_score + " points in the following attempts.\n\n");
                            while(res.moveToNext()) {
                                buffer.append(res.getString(2) + " area - " + "attempt started on " + res.getString(3) + " - points earned " + res.getString(4) + "\n");
                            }
                            message.showMessage(QuizResultActivity.this, "Hi " + username_from_id(user_id), buffer.toString());

                    }
                }
            };

    public String username_from_id(int id) {
        Cursor res = myDB.viewRecord_by_id(id);
        if (res.getCount() == 0) {
            message.showMessage(this, "Error", "No record found");
            return null;
        } else {
            res.moveToFirst();
            return res.getString(2);
        }
    }

    public void addRecord() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
        LocalDateTime now = LocalDateTime.now();
        boolean isInserted = scoresDB.insertData(Integer.toString(user_id), area, dtf.format(now), Integer.toString(score));
        if (isInserted == false) {
            Toast.makeText(QuizResultActivity.this, "Error, no record added", Toast.LENGTH_LONG).show();
        }
    }

    public void overall_score() {
        Cursor res = scoresDB.viewRecord(user_id);
        if (res.getCount() == 0) {
            message.showMessage(this, "Error", "No record found");
            return;
        }
        overall_score = 0;
        while(res.moveToNext()){
            overall_score += res.getInt(4);
        }
    }
}