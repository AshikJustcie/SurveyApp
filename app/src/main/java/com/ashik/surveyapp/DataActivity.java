package com.ashik.surveyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ashik.surveyapp.SQLite.DataHelperSurvey;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity implements DataAdapter.ItemClickListener, View.OnClickListener{

    private RecyclerView recyclerViewC,recyclerViewP;
    private DataAdapter adapterP, adapterC;
    private List<DataModel> arrayListP, arrayListC;

    DataHelperSurvey mDatabaseHelper;

    TextView prog, comp;

    DataModel dataModel;

    int t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mDatabaseHelper = new DataHelperSurvey(this);

        prog = findViewById(R.id.progressView);
        comp = findViewById(R.id.completedView);

        prog.setOnClickListener(this);
        comp.setOnClickListener(this);



        arrayListP = new ArrayList<>();
        recyclerViewP = findViewById(R.id.recInProgress);
        adapterP = new DataAdapter(this,arrayListP);
        recyclerViewP.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewP.setAdapter(adapterP);


        arrayListC = new ArrayList<>();
        recyclerViewC = findViewById(R.id.recCompleted);
        adapterC = new DataAdapter(this,arrayListC);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewC.setAdapter(adapterC);

        GetData();

        recyclerViewP.setVisibility(View.GONE);
        t=2;
    }

    private void GetData() {
        Cursor data = mDatabaseHelper.getData();

        if (data.getCount() == 0) {

        }

        else {

            while (data.moveToNext()) {
                int count = data.getInt(1);
                String q1 = data.getString(2);
                String a1 = data.getString(3);
                String q2 = data.getString(4);
                String a2 = data.getString(5);
                String q3 = data.getString(6);
                String a3 = data.getString(7);
                String q4 = data.getString(8);
                String a4 = data.getString(9);
                String q5 = data.getString(10);
                String a5 = data.getString(11);
                String date = data.getString(12);


                DataModel items = new DataModel(count, q1, a1, q2, a2, q3, a3, q4, a4, q5, a5, date);

                if (count == 5) {
                    arrayListC.add(items);
                } else {
                    arrayListP.add(items);
                }

            }

            adapterP.notifyDataSetChanged();
            adapterC.notifyDataSetChanged();
            adapterP.setClickListener(this);
            adapterC.setClickListener(this);
        }

    }

    @Override
    public void onClick(View view, int position) {

        if (t == 1) {
            dataModel = arrayListP.get(position);
        }
        else if (t == 2) {
            dataModel = arrayListC.get(position);
        }


        String q1 = dataModel.getQues1();
        String q2 = dataModel.getQues2();
        String q3 = dataModel.getQues3();
        String q4 = dataModel.getQues4();
        String q5 = dataModel.getQues5();

        String a1 = dataModel.getAns1();
        String a2 = dataModel.getAns2();
        String a3 = dataModel.getAns3();
        String a4 = dataModel.getAns4();
        String a5 = dataModel.getAns5();

        String date = dataModel.getDate();

        Intent intent = new Intent(DataActivity.this, DetailsActivity.class);
        intent.putExtra("qt1",q1);
        intent.putExtra("qt2",q2);
        intent.putExtra("qt3",q3);
        intent.putExtra("qt4",q4);
        intent.putExtra("qt5",q5);
        intent.putExtra("an1",a1);
        intent.putExtra("an2",a2);
        intent.putExtra("an3",a3);
        intent.putExtra("an4",a4);
        intent.putExtra("an5",a5);
        intent.putExtra("dt",date);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progressView:
                recyclerViewP.setVisibility(View.VISIBLE);
                recyclerViewC.setVisibility(View.GONE);
                t = 1;
                prog.setBackground(getResources().getDrawable(R.drawable.custom_background));
                comp.setBackground(getResources().getDrawable(R.drawable.custom_border));
                break;

            case R.id.completedView:
                recyclerViewC.setVisibility(View.VISIBLE);
                recyclerViewP.setVisibility(View.GONE);
                t=2;
                prog.setBackground(getResources().getDrawable(R.drawable.custom_border));
                comp.setBackground(getResources().getDrawable(R.drawable.custom_background));
                break;
        }
    }
}