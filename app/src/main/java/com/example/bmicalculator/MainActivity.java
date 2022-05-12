package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar, seekBar2;
    private TextView height_value, weight_value;
    private int initial_height_value = 125, initial_weight_value = 200, height = initial_height_value, weight = initial_weight_value;
    private int age = 0;
    private float bmi;
    private TextView display_age;
    private ImageView add_age;
    private ImageView reduce_age;
    private String name;
    private String gender;
    private View male_click, female_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        DBHelper dbHelper = new DBHelper(this);

        seekBar = (SeekBar)findViewById(R.id.seek_scroll);
        seekBar2 = (SeekBar)findViewById(R.id.seek_scroll2);
        height_value = (TextView)findViewById(R.id.height_align);
        weight_value = (TextView)findViewById(R.id.weight_align);
        add_age = (ImageView)findViewById(R.id.addAge);
        reduce_age = (ImageView)findViewById(R.id.reduceAge);
        display_age = (TextView)findViewById(R.id.age);
        male_click = findViewById(R.id.male_line);
        female_click = findViewById(R.id.female_line);

        male_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Male";
                male_click.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gender_selected));
                female_click.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_container));
            }
        });

        female_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Female";
                female_click.setBackgroundColor(0);
                female_click.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.gender_selected));
                male_click.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_container));
            }
        });

        seekBar.setMax(250);
        seekBar.setProgress(initial_height_value);
        height_value.setText("HEIGHT: " + initial_height_value + " cm");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                height = progress;
                height_value.setText("HEIGHT: " + progress + " cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setMax(400);
        seekBar2.setProgress(initial_weight_value);
        weight_value.setText("WEIGHT: " + initial_weight_value + " kg");
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                weight = progress;
                weight_value.setText("WEIGHT: " + progress + " kg");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        add_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                age++;
                display_age.setText("AGE: " + age);
            }
        });

        reduce_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(age <= 0) return;
                age--;
                display_age.setText("AGE: " + age);
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = ((TextView)findViewById(R.id.name)).getText().toString();
                bmi = calculateBMI(height, weight);

                Intent intent = new Intent(MainActivity.this, BMIResults.class);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("BMI", bmi);
                intent.putExtra("name", name);

                if(name.isEmpty()){
                    Toast.makeText(MainActivity.this, "Name is empty!", Toast.LENGTH_SHORT).show();
                }else if(gender == null){
                    Toast.makeText(MainActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                }else if(height == 0){
                    Toast.makeText(MainActivity.this, "Invalid height", Toast.LENGTH_SHORT).show();
                }else if(weight == 0){
                    Toast.makeText(MainActivity.this, "Invalid weight", Toast.LENGTH_SHORT).show();
                }else if(age == 0){
                    Toast.makeText(MainActivity.this, "Invalid age", Toast.LENGTH_SHORT).show();
                }else{
                    if(dbHelper.insertData(name, gender, height, weight, age, bmi) == 1){
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BMIHistory.class);
                startActivity(intent);
            }
        });
    }
    float calculateBMI(float height, float weight){
        return (float) (weight / Math.pow(height / 100, 2));
    }
}