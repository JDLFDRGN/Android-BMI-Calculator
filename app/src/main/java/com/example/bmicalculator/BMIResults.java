package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BMIResults extends AppCompatActivity {

    private DecimalFormat df;
    private float bmi;
    private int classificationImg;
    private String classification;
    private String getName;
    private String recommendation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresults);
        getSupportActionBar().hide();

        df = new DecimalFormat("0.00");
        bmi = getIntent().getExtras().getFloat("BMI");
        getName = getIntent().getExtras().getString("name");

        if(bmi < 18.5){
            classification = "Underweight";
            classificationImg = R.drawable.ic_baseline_mood_underweight_24;
            recommendation = ". Eat at least 5 portions of a variety of fruit and vegetables every day. Eat some beans, pulses, fish, eggs, meat and other protein.";
        }else if(bmi >= 18.25 && bmi < 25){
            classification = "Normal";
            classificationImg = R.drawable.ic_baseline_mood_normal_24;
            recommendation = ". You have a healthy eating habits.";
        }else if(bmi >= 25 && bmi < 30){
           classification = "Overweight";
           classificationImg = R.drawable.ic_baseline_mood_overweight_24;
           recommendation = ". Eat small amounts of food and drinks that are high in fat and sugar.";
        }else{
            classification = "Obese";
            classificationImg = R.drawable.ic_baseline_mood_obese_24;
            recommendation = ". Choose minimally processed, whole foods-whole grains, vegetables, fruits, nuts, healthy source of protein, and plant oils.";
        }

        ((TextView)findViewById(R.id.classification)).setText(classification);
        ((TextView)findViewById(R.id.bmi_total)).setText("Hello " + getName + ", your bmi is " + bmi + recommendation);
        ((ImageView)findViewById(R.id.classification_img)).setImageResource(classificationImg);

        findViewById(R.id.return_to_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BMIResults.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}