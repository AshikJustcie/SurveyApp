package com.ashik.surveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    TextView ques1, ans1, ques2,ans2,ques3,ans3,ques4,ans4,ques5,ans5;

    String q1,q2,q3,q4,q5;
    String a1,a2,a3,a4,a5, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbarSD);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ques1= findViewById(R.id.question1);
        ques2 = findViewById(R.id.question2);
        ques3 = findViewById(R.id.question3);
        ques4 = findViewById(R.id.question4);
        ques5 = findViewById(R.id.question5);

        ans1 = findViewById(R.id.answer1);
        ans2 = findViewById(R.id.answer2);
        ans3 = findViewById(R.id.answer3);
        ans4 = findViewById(R.id.answer4);
        ans5 = findViewById(R.id.answer5);

        q1 = getIntent().getStringExtra("qt1");
        q2 = getIntent().getStringExtra("qt2");
        q3 = getIntent().getStringExtra("qt3");
        q4 = getIntent().getStringExtra("qt4");
        q5 = getIntent().getStringExtra("qt5");

        a1 = getIntent().getStringExtra("an1");
        a2 = getIntent().getStringExtra("an2");
        a3 = getIntent().getStringExtra("an3");
        a4 = getIntent().getStringExtra("an4");
        a5 = getIntent().getStringExtra("an5");

        date = getIntent().getStringExtra("dt");

        ques1.setText("Q1: ".concat(q1));
        ques2.setText("Q2: ".concat(q2));
        ques3.setText("Q3: ".concat(q3));
        ques4.setText("Q4: ".concat(q4));
        ques5.setText("Q5: ".concat(q5));

        String aa = "Ans: ";
        ans1.setText(aa.concat(a1));
        ans2.setText(aa.concat(a2));
        ans3.setText(aa.concat(a3));
        ans4.setText(aa.concat(a4));
        ans5.setText(aa.concat(a5));

        toolbar.setSubtitle(date);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}