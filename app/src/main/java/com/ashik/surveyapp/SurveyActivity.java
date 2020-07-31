package com.ashik.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ashik.surveyapp.API.Client.RetrofitClient;
import com.ashik.surveyapp.API.Model.SurveyModel;
import com.ashik.surveyapp.SQLite.DataHelperSurvey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity {

    View vS1,vS2,vS3,vS4,vS5;

    CheckBox checkbox1,checkbox2;

    RadioGroup radioGroup;
    RadioButton radioButton, radioBtn1,radioBtn2,radioBtn3,radioBtn4,radioBtn5;

    LinearLayout q1Lay,q2Lay,q3Lay,q4Lay,q5Lay;
    TextView surveyQ,questionNo;

    Button submitBtn;

    List<SurveyModel> arrayList;

    ProgressDialog progressDialog;

    String TypeMajor;

    Spinner spinner;

    EditText placeName,contactnumber;

    boolean required1,required2,required3,required4,required5;

    String radioResult, checkResult;

    String q1,q2,q3,q4,q5;
    String Result1, Result2, Result3, Result4, Result5;

    ScrollView scrollView;
    RelativeLayout afterView;

    DataHelperSurvey mDatabaseHelper;

    Button completedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        mDatabaseHelper = new DataHelperSurvey(this);

        Result1 = "Not Answered";
        Result2 = "Not Answered";
        Result3 = "Not Answered";
        Result4 = "Not Answered";
        Result5 = "Not Answered";

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Questions...");

        FindViewByIds();

        arrayList = new ArrayList<>();

        GetSurveyData();

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SurveyActivity.this, DataActivity.class));
            }
        });
    }

    private void FindViewByIds() {

        spinner = findViewById(R.id.spinner);

        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);

        radioGroup = findViewById(R.id.radioGroup);

        radioBtn1 = findViewById(R.id.radioBtn1);
        radioBtn2 = findViewById(R.id.radioBtn2);
        radioBtn3 = findViewById(R.id.radioBtn3);
        radioBtn4 = findViewById(R.id.radioBtn4);
        radioBtn5 = findViewById(R.id.radioBtn5);

        placeName = findViewById(R.id.placeName);
        contactnumber = findViewById(R.id.contactnumber);

        vS1 = findViewById(R.id.vS1);
        vS2 = findViewById(R.id.vS2);
        vS3 = findViewById(R.id.vS3);
        vS4 = findViewById(R.id.vS4);
        vS5 = findViewById(R.id.vS5);

        q1Lay = findViewById(R.id.q1Lay);
        q2Lay = findViewById(R.id.q2Lay);
        q3Lay = findViewById(R.id.q3Lay);
        q4Lay = findViewById(R.id.q4Lay);
        q5Lay = findViewById(R.id.q5Lay);

        q1Lay.setVisibility(View.VISIBLE);
        q2Lay.setVisibility(View.GONE);
        q3Lay.setVisibility(View.GONE);
        q4Lay.setVisibility(View.GONE);
        q5Lay.setVisibility(View.GONE);

        questionNo = findViewById(R.id.questionNo);
        surveyQ = findViewById(R.id.surveyQ);

        submitBtn = findViewById(R.id.submitBtn);

        scrollView = findViewById(R.id.scrollView);
        afterView = findViewById(R.id.afterView);

        completedBtn = findViewById(R.id.completeSurveyBtn);

    }

    public String GetTimeStamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    private void GetSurveyData() {

        progressDialog.show();

        Call<List<SurveyModel>> call = RetrofitClient.getInstance().getApi().getSurveyQuestion(GetTimeStamp());

        call.enqueue(new Callback<List<SurveyModel>>() {
            @Override
            public void onResponse(Call<List<SurveyModel>> call, Response<List<SurveyModel>> response) {

                progressDialog.dismiss();

                if (!response.isSuccessful()) {

                    switch (response.code()) {
                        case 403:
                            Toast.makeText(getApplicationContext(), "Session Expired", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
                            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "unknown error: "+response.code(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else {

                    List<SurveyModel> surveys = response.body();

                    if (surveys.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No data found!", Toast.LENGTH_SHORT).show();
                    }

                    for (SurveyModel survey : surveys) {
                        String question = survey.getQuestion();
                        String type = survey.getType();
                        String option = survey.getOptions();
                        boolean required = survey.isRequired();

                        SurveyModel survey1 = new SurveyModel(question, type, option, required);
                        arrayList.add(survey1);
                    }

                    SetValues();
                }
            }

            @Override
            public void onFailure(Call<List<SurveyModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetValues() {

        if (arrayList.size() == 0) {

        }
        else {
            for (int i = 0 ; i < arrayList.size() ; i++) {

                String option = arrayList.get(i).getOptions();
                String type = arrayList.get(i).getType();

                if (type.equals("dropdown")) {

                    String[] splitStr = option.trim().split("\\s*,\\s*");

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            this, android.R.layout.simple_spinner_item, splitStr);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                }

                else if (type.equals("multiple choice")) {
                    String[] splitStr = option.trim().split("\\s*,\\s*");

                    radioBtn1.setText(splitStr[0]);
                    radioBtn2.setText(splitStr[1]);
                    radioBtn3.setText(splitStr[2]);
                    radioBtn4.setText(splitStr[3]);
                    radioBtn5.setText(splitStr[4]);
                }
                else if (type.equals("Checkbox")) {
                    String[] splitStr = option.trim().split("\\s*,\\s*");

                    checkbox1.setText(splitStr[0]);
                    checkbox2.setText(splitStr[1]);

                    checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                checkbox2.setChecked(false);
                                checkResult = "Yes";
                            }
                        }
                    });

                    checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                checkbox1.setChecked(false);
                                checkResult = "No";
                            }
                        }
                    });
                }
                else if (type.equals("text")) {

                }
                else if (type.equals("number")) {

                }



            }

            //questions
            q1 = arrayList.get(0).getQuestion();
            q2 = arrayList.get(1).getQuestion();
            q3 = arrayList.get(2).getQuestion();
            q4 = arrayList.get(3).getQuestion();
            q5 = arrayList.get(4).getQuestion();

            //boolean required values
            required1 = arrayList.get(0).isRequired();
            required2 = arrayList.get(1).isRequired();
            required3 = arrayList.get(2).isRequired();
            required4 = arrayList.get(3).isRequired();
            required5 = arrayList.get(4).isRequired();

            //types
            String type1 = arrayList.get(0).getType();
            String type2 = arrayList.get(1).getType();
            String type3 = arrayList.get(2).getType();
            String type4 = arrayList.get(3).getType();
            String type5 = arrayList.get(4).getType();

            ShowAnswerByTypes(type1,type2,type3,type4,type5);
        }

    }

    private void ShowAnswerByTypes(final String type1, final String type2, final String type3, final String type4, final String type5) {


        //Survey Completion Date
        final String date = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());

        //Setting default or first or type1 view and getting ans of first question
        TypeMajor = type1;
        ViewByType(type1);

        final String quesNo = "Question ";
        questionNo.setText(quesNo.concat(" 1"));
        surveyQ.setText(q1);

        vS1.setBackgroundColor(Color.parseColor("#0986D3"));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TypeMajor.equals(type1)) {

                    Result1 = GetResult(type1);

                    if (required1) {
                        if (TextUtils.isEmpty(Result1)) {
                            Toast.makeText(getApplicationContext(),"Field must be required!",Toast.LENGTH_LONG).show();
                        }
                        else {

                            //Setting second or type2 view and getting ans of second question
                            TypeMajor = type2;
                            ViewByType(type2);

                            questionNo.setText(quesNo.concat(" 2"));
                            surveyQ.setText(q2);

                            vS2.setBackgroundColor(Color.parseColor("#0986D3"));

                            SaveData(1,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                        }
                    }
                    else {
                        //Setting second or type2 view and getting ans of second question
                        TypeMajor = type2;
                        ViewByType(type2);

                        questionNo.setText(quesNo.concat(" 2"));
                        surveyQ.setText(q2);

                        vS2.setBackgroundColor(Color.parseColor("#0986D3"));

                        SaveData(1,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                    }


                }

                else if (TypeMajor.equals(type2)) {

                    Result2 = GetResult(type2);

                    if (required2) {
                        if (TextUtils.isEmpty(Result2)) {
                            Toast.makeText(getApplicationContext(),"Field must be required!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Setting third or type3 view and getting ans of third question
                            TypeMajor = type3;
                            ViewByType(type3);

                            questionNo.setText(quesNo.concat(" 3"));
                            surveyQ.setText(q3);

                            vS3.setBackgroundColor(Color.parseColor("#0986D3"));

                            UpdateData(2,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                        }
                    }
                    else {
                        //Setting third or type3 view and getting ans of third question
                        TypeMajor = type3;
                        ViewByType(type3);

                        questionNo.setText(quesNo.concat(" 3"));
                        surveyQ.setText(q3);

                        vS3.setBackgroundColor(Color.parseColor("#0986D3"));

                        UpdateData(2,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                    }
                }

                else if (TypeMajor.equals(type3)) {

                    Result3 = GetResult(type3);

                    if (required3) {
                        if (TextUtils.isEmpty(Result3)) {
                            Toast.makeText(getApplicationContext(),"Field must be required!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Setting fourth or type4 view and getting ans of fourth question
                            TypeMajor = type4;
                            ViewByType(type4);

                            questionNo.setText(quesNo.concat(" 4"));
                            surveyQ.setText(q4);

                            vS4.setBackgroundColor(Color.parseColor("#0986D3"));

                            UpdateData(3,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                        }
                    }
                    else {
                        //Setting fourth or type4 view and getting ans of fourth question
                        TypeMajor = type4;
                        ViewByType(type4);

                        questionNo.setText(quesNo.concat(" 4"));
                        surveyQ.setText(q4);

                        vS4.setBackgroundColor(Color.parseColor("#0986D3"));

                        UpdateData(3,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                    }
                }

                else if (TypeMajor.equals(type4)) {

                    Result4 = GetResult(type4);

                    if (required4) {
                        if (TextUtils.isEmpty(Result4)) {
                            Toast.makeText(getApplicationContext(),"Field must be required!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            //Setting fifth or type5 view and getting ans of fifth question
                            TypeMajor = type5;
                            ViewByType(type5);

                            submitBtn.setText("Submit");

                            questionNo.setText(quesNo.concat(" 5"));
                            surveyQ.setText(q5);

                            vS5.setBackgroundColor(Color.parseColor("#0986D3"));

                            UpdateData(4,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                        }
                    }
                    else {
                        //Setting fifth or type5 view and getting ans of fifth question
                        TypeMajor = type5;
                        ViewByType(type5);

                        submitBtn.setText("Submit");

                        questionNo.setText(quesNo.concat(" 5"));
                        surveyQ.setText(q5);

                        vS5.setBackgroundColor(Color.parseColor("#0986D3"));

                        UpdateData(4,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);
                    }
                }

                else if (TypeMajor.equals(type5)) {

                    Result5 = GetResult(type5);

                    if (required5) {
                        if (TextUtils.isEmpty(Result5)) {
                            Toast.makeText(getApplicationContext(),"Field must be required!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            UpdateData(5,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);

                            afterView.setVisibility(View.VISIBLE);
                            submitBtn.setVisibility(View.GONE);
                            scrollView.setVisibility(View.GONE);
                        }
                    }
                    else {

                        UpdateData(5,q1,Result1,q2,Result2,q3,Result3,q4,Result4,q5,Result5,date);

                        afterView.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);
                        scrollView.setVisibility(View.GONE);
                    }
                }

            }
        });

    }

    //Set View By Question Type
    private void ViewByType(String type) {

        if (type.equals("dropdown")) {
            q1Lay.setVisibility(View.VISIBLE);
            q2Lay.setVisibility(View.GONE);
            q3Lay.setVisibility(View.GONE);
            q4Lay.setVisibility(View.GONE);
            q5Lay.setVisibility(View.GONE);
        }

        else if (type.equals("multiple choice")) {
            q1Lay.setVisibility(View.GONE);
            q2Lay.setVisibility(View.VISIBLE);
            q3Lay.setVisibility(View.GONE);
            q4Lay.setVisibility(View.GONE);
            q5Lay.setVisibility(View.GONE);
        }
        else if (type.equals("Checkbox")) {
            q1Lay.setVisibility(View.GONE);
            q2Lay.setVisibility(View.GONE);
            q3Lay.setVisibility(View.VISIBLE);
            q4Lay.setVisibility(View.GONE);
            q5Lay.setVisibility(View.GONE);
        }
        else if (type.equals("text")) {
            q1Lay.setVisibility(View.GONE);
            q2Lay.setVisibility(View.GONE);
            q3Lay.setVisibility(View.GONE);
            q4Lay.setVisibility(View.VISIBLE);
            q5Lay.setVisibility(View.GONE);
        }
        else if (type.equals("number")) {
            q1Lay.setVisibility(View.GONE);
            q2Lay.setVisibility(View.GONE);
            q3Lay.setVisibility(View.GONE);
            q4Lay.setVisibility(View.GONE);
            q5Lay.setVisibility(View.VISIBLE);
        }

    }

    //Get Results
    public String GetResult(String type) {

        String result = "";

        if (type.equals("dropdown")) {
            result = spinner.getSelectedItem().toString();
        }
        else if (type.equals("multiple choice")) {
            result = radioResult;
        }
        else if (type.equals("Checkbox")) {
            result = checkResult;
        }
        else if (type.equals("text")) {
            result = placeName.getText().toString();
        }
        else if (type.equals("number")) {
            result = contactnumber.getText().toString();
        }

        return result;

    }

    //Choose multi choice item
    public void CheckRadioButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        radioResult = radioButton.getText().toString();
    }


    public void SaveData(int count, String q1, String a1, String q2, String a2, String q3,String a3,String q4,
                                  String a4,String q5,String a5, String date) {

        mDatabaseHelper.addData(count,q1,a1,q2,a2,q3,a3,q4,a4,q5,a5,date);
    }

    public void UpdateData(int count, String q1, String a1, String q2, String a2, String q3,String a3,String q4,
                         String a4,String q5,String a5, String date) {

        mDatabaseHelper.updateData(count,q1,a1,q2,a2,q3,a3,q4,a4,q5,a5,date);
    }
}