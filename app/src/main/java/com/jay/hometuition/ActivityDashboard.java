package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ActivityDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btnTutor,btnStudent,btnLogout;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnStudent = findViewById(R.id.buttonStudent);
        btnTutor = findViewById(R.id.buttonTutor);
        btnLogout = findViewById(R.id.btnLogout);

        checkAccType();

        btnTutor.setOnClickListener(this);
        btnStudent.setOnClickListener(this);
        btnLogout.setOnClickListener(this);



    }
    public void checkAccType(){
        db.collection("users")
                .whereEqualTo("id",auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot data : task.getResult()){
                                    if (data.getString("type").equals("Tutor")){
                                        btnStudent.setVisibility(View.INVISIBLE);
                                    }
                                    else if(data.getString("type").equals("Student")){
                                        btnTutor.setVisibility(View.INVISIBLE);
                                    }
                                    break;
                                }
                            }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonStudent:
                //Goto student act
                Intent intent = new Intent(ActivityDashboard.this,StudentActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonTutor:
                //Goto tutor act
                intent = new Intent(ActivityDashboard.this,TutorActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                logout();
                break;
        }



    }
    public void logout(){
        auth.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }


}
