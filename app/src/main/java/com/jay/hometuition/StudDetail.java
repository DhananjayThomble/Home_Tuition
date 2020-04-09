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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class StudDetail extends AppCompatActivity implements View.OnClickListener {

    private EditText mName, mClass, mSubject, mSchool, mBoard;
    private Button btnNext, btnModify, btnRemove;
    private Spinner mGender, mMarks, mCity;
    private Students students;
    private String firebaseUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private DocumentReference documentReference;
    private Boolean flag = true;

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
        btnModify = findViewById(R.id.buttonModifyStud);
        btnRemove = findViewById(R.id.btnRemove);

//        btnNext.setEnabled(false);
        btnModify.setVisibility(View.INVISIBLE);
        btnRemove.setVisibility(View.INVISIBLE);

        btnRemove.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        Intent myIntent = getIntent();

        if (myIntent.hasExtra("studDetail")) {
//            Log.d("test","studDetail intent extra found");
            btnNext.setVisibility(View.INVISIBLE);
            flag = false;
            HashMap<String, String> studDetails = (HashMap<String, String>) myIntent.getSerializableExtra("studDetail");
            mName.setText(studDetails.get("name").toString());
            mClass.setText(studDetails.get("class").toString());
            mSchool.setText(studDetails.get("school"));
            mBoard.setText(studDetails.get("board"));
            mSubject.setText(studDetails.get("subject"));
            btnModify.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.VISIBLE);
        }

        //Testing of Students class
//                Log.d("test",students.getmName()+" "+students.getmMarks()+" "+students.get);

    }

//    private void checkDataHistory() {
//
//        db.collection("students")
//                .whereEqualTo("id", auth.getCurrentUser().getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//                            QuerySnapshot documentSnapshot = task.getResult();
//                            if (documentSnapshot.isEmpty()) {
//                                //Null
//
////                         // User data is not exist yet
//                                btnNext.setEnabled(true);
////
//                            } else {
//                                //User data is already Exist;
//                                fillAllInput();
//                                Toast.makeText(getApplicationContext(), "Wait your data is loading...", Toast.LENGTH_SHORT).show();
//
//                            }
////
//                        }
//
////
//                    }
//                });
//
//    }

//    private void fillAllInput() {
//
//        db.collection("students")
//                .whereEqualTo("status", "1")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
////
//                                if (document.getString("name") != null) {
//
//                                    String mName1, mClass1, mSchool1, mGender1, mBoard1, mMarks1, mSubject1, mCity1;
//                                    mName1 = document.getString("name");
//                                    mClass1 = document.getString("class");
//                                    mSchool1 = document.getString("school");
//                                    mGender1 = document.getString("gender");
//                                    mBoard1 = document.getString("board");
//                                    mMarks1 = document.getString("marks");
//                                    mSubject1 = document.getString("subject");
//                                    mCity1 = document.getString("city");
//
//                                    mName.setText(mName1);
//                                    mClass.setText(mClass1);
//                                    mSchool.setText(mSchool1);
//                                    mBoard.setText(mBoard1);
//                                    mSubject.setText(mSubject1);
//
//                                }
////                               //Fetch name from data
////                                if (document.getString("name") != null)
////                                Log.d("test", document.getId() + " => " + document.getString("name"));
//                            }
//                        } else {
//                            Log.d("test", "Error getting documents: ", task.getException());
//                        }
//                        if (task.isComplete())
//                            Toast.makeText(getApplicationContext(), "Your Data is Ready", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//    }


    private void insertData2StudClass() {

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
        user.put("city", students.getmCity());


        // Add a new document with a generated ID
        db.collection("students")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test", "DocumentSnapshot added ");
                        Toast.makeText(StudDetail.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StudDetail.this, StudentActivity.class);
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

    public void modifyData() {

    }
    public void removeData(){

       documentReference = db.collection("students").getParent();
       documentReference.u


//                //delete data
//                db.collection("students")
//                        .whereEqualTo("id",auth.getCurrentUser().getUid())
//                        .get()
//                        .continueWith(new Continuation<QuerySnapshot, Object>() {
//
//                            @Override
//                            public Object then(@NonNull Task<QuerySnapshot> task) throws Exception {
//                                documentReference = db.collection("student").document(db.do);
//                                db.batch().delete();
//
//                                return null;
//                            }
//                        })
//                        .update("status","0")
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(getApplicationContext(),"Record Deleted!",Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                                else {
//                                    Toast.makeText(getApplicationContext(),"Some error occured. try again!",Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                            }
//                        });
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {
////                                Toast.makeText(getApplicationContext(),"Record Deleted Successfully",Toast.LENGTH_SHORT).show();
////                                finish();
////                                startActivity(getIntent());
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.w("test", "Error deleting document", e);
////                            }
////                        });

            }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRemove:
                removeData();
                break;
            case R.id.buttonModifyStud:
                modifyData();
                insertData2StudClass();
                insertDataCloud();

                break;
            case R.id.buttonSubmitStud:
                insertData2StudClass();
                insertDataCloud();

                break;
        }
    }
}



