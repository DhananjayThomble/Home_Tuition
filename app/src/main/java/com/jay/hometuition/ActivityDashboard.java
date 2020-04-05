package com.jay.hometuition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btnTutor,btnStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnStudent = findViewById(R.id.buttonStudent);
        btnTutor = findViewById(R.id.buttonTutor);

        btnTutor.setOnClickListener(this);
        btnStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnStudent){
            //Goto student act
        }
        else if ( v == btnTutor ){
            //Goto tutor act
        }
    }
}
