package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BMIHistory extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmihistory);
        getSupportActionBar().hide();
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getData();
        listView = findViewById(R.id.history_list);
        ArrayList arrayList = new ArrayList();

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                arrayList.add("Name: "  + cursor.getString(1) + "\n" + "Gender: " + cursor.getString(2) + "\n" + "Height: " + cursor.getInt(3) + " cm" + "\n" + "Weight: " + cursor.getInt(4) + " kg" + "\n" + "Age: " + cursor.getInt(5) + "\n" + "BMI: " + cursor.getFloat(6));
                cursor.moveToNext();
            }
        }

        cursor.close();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}