package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudDetail extends AppCompatActivity {

    private EditText mName,mClass,mSubject,mSchool,mBoard;
    private Button btnNext;
    private Spinner mGender,mMarks;
    private Students students;
    private String firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_detail);

        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        students = new Students();
        //Get firebase user
        firebaseUser = "";
        firebaseUser = students.getFirebaseUser();


        mName = findViewById(R.id.editTextName);
        mGender = findViewById(R.id.spinnerGender);
        mClass = findViewById(R.id.editTextClass);
        mSubject = findViewById(R.id.editTextSubject);
        mSchool = findViewById(R.id.editTextSchool);
        mBoard = findViewById(R.id.editTextBoard);
        mMarks = findViewById(R.id.spinnerMarks);
        btnNext = findViewById(R.id.buttonNext2);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                //Testing of Students class
//                Log.d("test",students.getmName()+" "+students.getmMarks()+" "+students.get);
                //Storing Data in Cloud

//                insertDataCloud();
//                 Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("id", firebaseUser);
                user.put("name", students.getmName());
                user.put("gender", students.getmGender());
                user.put("class", students.getmClass());
                user.put("subject", students.getmSubject());
                user.put("school", students.getmSchool());
                user.put("board", students.getmBoard());
                user.put("marks", students.getmMarks());


                // Add a new document with a generated ID
                db.collection("students")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(StudDetail.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("test", "Error adding document", e);
                            }
                        });

            }
        });


}


    void insertData(){
        students.setmName(mName.getText().toString());
        students.setmGender(mGender.getSelectedItem().toString());
        students.setmClass(mClass.getText().toString());
        students.setmSubject(mSubject.getText().toString());
        students.setmSchool(mSchool.getText().toString());
        students.setmBoard(mBoard.getText().toString());
        students.setmMarks(mMarks.getSelectedItem().toString());
    }

}

