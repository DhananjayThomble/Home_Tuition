package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView textView;
    private Button btnRemove;
    private BottomNavigationView bottomNav;
    private FirebaseFirestore db;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Home Tuition");

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        bottomNav = findViewById(R.id.student_bottom_nav);
        btnRemove = findViewById(R.id.cardBtnRemove);
        textView = findViewById(R.id.cardTextView);
        btnRemove.setVisibility(View.INVISIBLE);
        getStudentInfo();



//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                mAuth = FirebaseAuth.getInstance();
//                mAuthListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
////                        Todo set User name to title
////                        if (user != null) {
////                            getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
////                        } else {
////
////                        }
//                    }
//                };
//            }
//        };

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change value of status to 0 from 1
                db.collection("students").document(mAuth.getCurrentUser().getUid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Record Deleted Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("test", "Error deleting document", e);
                            }
                        });

            }
        });

    }

    private void getStudentInfo() {
        db.collection("students")
                .whereEqualTo("status", "1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("name") != null) {

                                    btnRemove.setVisibility(View.VISIBLE);
                                    String mName1, mClass1, mSchool1, mGender1, mBoard1, mMarks1, mSubject1, mCity1;
                                    mName1 = document.getString("name");
                                    mClass1 = document.getString("class");
                                    mSchool1 = document.getString("school");
                                    mGender1 = document.getString("gender");
                                    mBoard1 = document.getString("board");
                                    mMarks1 = document.getString("marks");
                                    mSubject1 = document.getString("subject");
                                    mCity1 = document.getString("city");

                                    textView.setText("Name:- "+mName1 + "\n" + "Class:- "+mClass1 + "\n" + "School:- "+mSchool1 + "\n" +
                                            "Gender:- "+mGender1 + "\n" + "Board:- "+mBoard1 + "\n" +
                                            "Marks:- "+mMarks1 + "\n" + "Subject:- "+mSubject1 + "\n" + "City:- "+mCity1);
                                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_tutor:
                    Intent gotoStudDetails = new Intent(StudentActivity.this, StudDetail.class);
                    startActivity(gotoStudDetails);
                    break;

//                case R.id.nav_exit:
//                    selectedFragment = new ChatFragment();
//                    break;

            }

            return true;
        }
    };
}
