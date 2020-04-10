package com.jay.hometuition;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

public class Activity extends AppCompatActivity {

    TextView mName,mClass,mSchool,mBoard,mMarks,mGender,mSubject,mCity;
//    MapView myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        mName = findViewById(R.id.txtName);
        mClass = findViewById(R.id.txtClass);
        mBoard = findViewById(R.id.txtBoard);
        mGender = findViewById(R.id.txtGender);
        mMarks = findViewById(R.id.txtMarks);
        mSchool = findViewById(R.id.txtSchool);
        mSubject = findViewById(R.id.txtSubject);
        mCity = findViewById(R.id.txtCity);

        Intent myIntent = getIntent();
        HashMap<String, String> studDetails = (HashMap<String, String>) myIntent.getSerializableExtra("studDetail");
//        Log.d("test",studDetails.toString());

        mName.setText(studDetails.get("name").toString());
        mClass.setText(studDetails.get("class").toString());
        mBoard.setText(studDetails.get("board").toString());
        mMarks.setText(studDetails.get("marks").toString());
        mSchool.setText(studDetails.get("school").toString());
        mSubject.setText(studDetails.get("subject").toString());
        mGender.setText(studDetails.get("gender").toString());
        mCity.setText(studDetails.get("city").toString());
    }
}
