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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TutorEdit extends AppCompatActivity {

    private EditText mName, mQualification, tutoMobile, mSubject, tutoEmail;
    private Button btnNext;
    private Spinner mGender, mCity;
    private Tutor tutor;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit);


        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        tutor = new Tutor();

        //Get firebase user
        auth = FirebaseAuth.getInstance();

        mName = findViewById(R.id.TeditTextName);
        mGender = findViewById(R.id.TspinnerGender);
        mCity = findViewById(R.id.TselectCity);
        tutoMobile = findViewById(R.id.TeditTextMobile);
        mSubject = findViewById(R.id.TeditTextSubject);
        tutoEmail = findViewById(R.id.TeditTextEmail);
        mQualification = findViewById(R.id.TeditTextquali);
        btnNext = findViewById(R.id.buttonSubmitStud);

        checkDataHistory();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                insertDataCloud();
            }
        });
    }

    private void checkDataHistory() {

        db.collection("tutors")
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

        db.collection("tutors")
                .whereEqualTo("id", auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//
                                if (document.getString("name") != null) {

                                    String mName1, mQuali, mGender1, mMobile, mEmail, mSubject1, mCity1;
                                    mName1 = document.getString("name");
                                    mQuali = document.getString("quali");
//                                    mGender1 = document.getString("gender");
                                    mMobile = document.getString("mobile");
                                    mEmail = document.getString("email");
                                    mSubject1 = document.getString("subject");
//                                    mCity1 = document.getString("city");

                                    mName.setText(mName1);
                                    mQualification.setText(mQuali);
                                    tutoMobile.setText(mMobile);
                                    tutoEmail.setText(mEmail);
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
                        btnNext.setEnabled(true);
                    }
                });
    }
    private void insertData() {
        tutor.setmName(mName.getText().toString());
        tutor.setmFirebaseUser(auth.getCurrentUser().getUid());
        tutor.setmGender(mGender.getSelectedItem().toString());
        tutor.setmCity(mCity.getSelectedItem().toString());
        tutor.setmEmail(tutoEmail.getText().toString());
        tutor.setmSubject(mSubject.getText().toString());
        tutor.setmMobile(tutoMobile.getText().toString());
        tutor.setmQuali(mQualification.getText().toString());
//        tutor.setmQuali(mQualification.getSelectedItem().toString());
    }
    void insertDataCloud() {
        Map<String, Object> user = new HashMap<>();
        user.put("id", auth.getCurrentUser().getUid());
        user.put("name", tutor.getmName());
        user.put("gender", tutor.getmGender());
        user.put("email", tutor.getmEmail());
        user.put("subject", tutor.getmSubject());
        user.put("mobile", tutor.getmMobile());

        user.put("quali", tutor.getmQuali());
        user.put("city",tutor.getmCity());


        // Add a new document with a generated ID
        db.collection("tutors")
                .document(auth.getCurrentUser().getUid())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test", "DocumentSnapshot added ");
                        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                        finish();
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
