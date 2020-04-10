package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class StudDetail extends AppCompatActivity {

    private EditText mName, mClass, mSubject, mSchool, mBoard;
    private Button btnNext;
    private Spinner mGender, mMarks, mCity;
    private Students students;
    private String firebaseUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_detail);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        students = new Students();
        //Get firebase user
        firebaseUser = "";
        firebaseUser = students.getFirebaseUser();
        auth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.editTextName);
        mGender = findViewById(R.id.spinnerGender);
        mCity = findViewById(R.id.selectCity);
        mClass = findViewById(R.id.editTextClass);
        mSubject = findViewById(R.id.editTextSubject);
        mSchool = findViewById(R.id.editTextSchool);
        mBoard = findViewById(R.id.editTextBoard);
        mMarks = findViewById(R.id.spinnerMarks);
        btnNext = findViewById(R.id.buttonSubmitStud);
        btnNext.setEnabled(false);

        //TODO check if student already fill up the data
        checkDataHistory();
        //Testing of Students class
//                Log.d("test",students.getmName()+" "+students.getmMarks()+" "+students.get);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                insertDataCloud();
            }
        });
    }

    private void checkDataHistory() {

        db.collection("students")
                .whereEqualTo("id", auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            QuerySnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.isEmpty()) {
                                //Null

//                         // User data is not exist yet
                                btnNext.setEnabled(true);
//
                            } else {
                                //User data is already Exist;
                                fillAllInput();
                                Toast.makeText(getApplicationContext(), "Wait your data is loading...", Toast.LENGTH_SHORT).show();
                            //TODO create update function
                            }
//
                        }

//
                    }
                });

    }

    private void fillAllInput() {

        db.collection("students")
                .whereEqualTo("status", "1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//
                                if (document.getString("name") != null) {

                                    String mName1, mClass1, mSchool1, mGender1, mBoard1, mMarks1, mSubject1, mCity1;
                                    mName1 = document.getString("name");
                                    mClass1 = document.getString("class");
                                    mSchool1 = document.getString("school");
                                    mGender1 = document.getString("gender");
                                    mBoard1 = document.getString("board");
                                    mMarks1 = document.getString("marks");
                                    mSubject1 = document.getString("subject");
                                    mCity1 = document.getString("city");

                                    mName.setText(mName1);
                                    mClass.setText(mClass1);
                                    mSchool.setText(mSchool1);
                                    mBoard.setText(mBoard1);
                                    mSubject.setText(mSubject1);

                                }
//                               //Fetch name from data
//                                if (document.getString("name") != null)
//                                Log.d("test", document.getId() + " => " + document.getString("name"));
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                        if (task.isComplete())
                            Toast.makeText(getApplicationContext(), "Your Data is Ready", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    private void insertData() {
        students.setmName(mName.getText().toString());
        students.setmGender(mGender.getSelectedItem().toString());
        students.setmCity(mCity.getSelectedItem().toString());
        students.setmStatus("1");
        students.setmClass(mClass.getText().toString());
        students.setmSubject(mSubject.getText().toString());
        students.setmSchool(mSchool.getText().toString());
        students.setmBoard(mBoard.getText().toString());
        students.setmMarks(mMarks.getSelectedItem().toString());
    }

    void insertDataCloud() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", firebaseUser);
        user.put("status", students.getmStatus());
        user.put("name", students.getmName());
        user.put("gender", students.getmGender());
        user.put("class", students.getmClass());
        user.put("subject", students.getmSubject());
        user.put("school", students.getmSchool());
        user.put("board", students.getmBoard());
        user.put("marks", students.getmMarks());
        user.put("city",students.getmCity());


        // Add a new document with a generated ID
        db.collection("students")
                .document(auth.getCurrentUser().getUid())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "DocumentSnapshot added ");
                        Toast.makeText(StudDetail.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudDetail.this,StudentActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error adding document", e);
                    }
                });

    }

}



