package com.ashik.surveyapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DataHelperSurvey extends SQLiteOpenHelper {

    private static final String TAG = "Databashelper";

    private static final String KEY_ID = "KEY_ID";
    private static final String TABLE_NAME = "Survey_Data";
    private static final String COL0 = "count";
    private static final String COL1 = "question1";
    private static final String COL2 = "ans1";
    private static final String COL3 = "question2";
    private static final String COL4 = "ans2";
    private static final String COL5 = "question3";
    private static final String COL6 = "ans3";
    private static final String COL7 = "question4";
    private static final String COL8 = "ans4";
    private static final String COL9 = "question5";
    private static final String COL10 = "ans5";
    private static final String COL11 = "date";

    private static final int VERSION_NUMBER = 1;

    public DataHelperSurvey(@Nullable Context context) {
        super(context, TABLE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL0 + " INTEGER, " +
                COL1 + " TEXT, " + COL2 + " TEXT, " + COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT, "
                + COL7 + " TEXT, " + COL8 + " TEXT, " + COL9 + " TEXT, " + COL10 + " TEXT, " + COL11 + " TEXT)" ;

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public boolean addData(int count, String q1, String a1, String q2, String a2, String q3, String a3,String q4, String a4,String q5, String a5,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, count);
        contentValues.put(COL1, q1);
        contentValues.put(COL2, a1);
        contentValues.put(COL3, q2);
        contentValues.put(COL4, a2);
        contentValues.put(COL5, q3);
        contentValues.put(COL6, a3);
        contentValues.put(COL7, q4);
        contentValues.put(COL8, a4);
        contentValues.put(COL9, q5);
        contentValues.put(COL10, a5);
        contentValues.put(COL11, date);

        Log.d(TAG,"addData: Adding " + date + "to" + TABLE_NAME);

        long result = db.insert(TABLE_NAME,null,contentValues);

        //if data is inserted correctly it will insert -1
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void updateData(int count, String q1, String a1, String q2, String a2, String q3, String a3,String q4, String a4,String q5, String a5,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0, count);
        contentValues.put(COL1, q1);
        contentValues.put(COL2, a1);
        contentValues.put(COL3, q2);
        contentValues.put(COL4, a2);
        contentValues.put(COL5, q3);
        contentValues.put(COL6, a3);
        contentValues.put(COL7, q4);
        contentValues.put(COL8, a4);
        contentValues.put(COL9, q5);
        contentValues.put(COL10, a5);
        contentValues.put(COL11, date);

        db.update(TABLE_NAME,contentValues,COL1 + "='" + q1 + "'",null);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
