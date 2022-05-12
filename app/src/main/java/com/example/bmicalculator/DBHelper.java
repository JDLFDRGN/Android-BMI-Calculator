package com.example.bmicalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "user", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE history(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                             "name TEXT," +
                                             "gender TEXT, " +
                                             "height INTEGER," +
                                             "weight INTEGER," +
                                             "age INTEGER," +
                                             "bmi REAL)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS history");
        onCreate(sqLiteDatabase);
    }

    public int insertData(String name, String gender, int height, int weight, int age, float bmi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("gender", gender);
        cv.put("height", height);
        cv.put("weight", weight);
        cv.put("age", age);
        cv.put("bmi", bmi);
        long check = db.insert("history", null, cv);
        db.close();
        if(check == -1) return 0;
        else return 1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM history", null);
        return cursor;
    }

}
