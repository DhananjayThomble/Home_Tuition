package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView textView;
    private Button btnRemove;
    private BottomNavigationView bottomNav;
    private FirebaseFirestore db;
    private DocumentReference docRef;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private onClickInterfaceStud onclickInterface;
    private int i=0;
private ArrayList<ExampleItemStudent> exampleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //ExampleItemStudent
        exampleList = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Home Tuition");

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        bottomNav = findViewById(R.id.student_bottom_nav);
//        recyclerView = findViewById(R.id.recyclerView);


        //Listen click on cardview
        onclickInterface = new onClickInterfaceStud() {
            @Override
            public void setClick(int abc) {
                Toast.makeText(getApplicationContext(),"Position is "+abc,Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        };
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
    }
    private void recyclerViewConfig() {
        //Config  for RV
        Log.d("test","recy view config");

        recyclerView = findViewById(R.id.recyclerView);
        //Performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapterStud(exampleList,onclickInterface);

//        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListenerStud(getApplicationContext(), recyclerView, new RecyclerViewClickListenerStud() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), exampleList.get(position).getCount() + " is clicked!", Toast.LENGTH_SHORT).show();
                HashMap<String,String> studDetails = new HashMap<String, String>();

                studDetails.put("name",exampleList.get(position).getmName());
                studDetails.put("school",exampleList.get(position).getmSchool());
                studDetails.put("class",exampleList.get(position).getmClass());
                studDetails.put("gender",exampleList.get(position).getmGender());
                studDetails.put("board",exampleList.get(position).getmBoard());
                studDetails.put("marks",exampleList.get(position).getmMarks());
                studDetails.put("subject",exampleList.get(position).getmSubject());

//                Log.d("test",studDetails.toString());
                Intent gotoUserActi = new Intent(StudentActivity.this,StudDetail.class);
                gotoUserActi.putExtra("studDetail",studDetails);
                startActivity(gotoUserActi);
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), exampleList.get(position).getCount()+ " is long pressed!", Toast.LENGTH_SHORT).show();

            }
        }));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
                                    i++;
                                    Log.d("test","db fetched");
                                    String mName1, mClass1, mSchool1, mGender1, mBoard1, mMarks1, mSubject1, mCity1;
                                    mName1 = document.getString("name");
                                    mClass1 = document.getString("class");
                                    mSchool1 = document.getString("school");
                                    mGender1 = document.getString("gender");
                                    mBoard1 = document.getString("board");
                                    mMarks1 = document.getString("marks");
                                    mSubject1 = document.getString("subject");
                                    mCity1 = document.getString("city");
//
                                    exampleList.add(new ExampleItemStudent(R.drawable.clip_person_foreground,i,mName1,mClass1,mSchool1,mBoard1,mMarks1,mGender1,mSubject1,mCity1));


//                                    textView.
//                                    textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                        if (task.isComplete()){
                            recyclerViewConfig();
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

                case R.id.nav_exit:
                    finish();
                    break;

            }

            return true;
        }
    };
}
