package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class TutorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private onClickInterface onclickInterface2;
    private int i=0;
    private BottomNavigationView bottomNav;
    ArrayList<ExampleItem> exampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        bottomNav = findViewById(R.id.student_bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        exampleList = new ArrayList<>();
//       exampleList.add(new ExampleItem(R.drawable.hp,"hello"));


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        onclickInterface2 = new onClickInterface() {
            @Override
            public void setClick(int abc) {
                Toast.makeText(getApplicationContext(),"Position is "+abc,Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();

            }
        };
        db.collection("students")
                .whereEqualTo("status", "1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//
                                if (document.getString("name") != null)
                                {
                                    i++;
                                    String mName,mClass,mSchool,mGender,mBoard,mMarks,mSubject,mCity;
                                    mName = document.getString("name");
                                    mClass = document.getString("class");
                                    mSchool = document.getString("school");
                                    mGender = document.getString("gender");
                                    mBoard = document.getString("board");
                                    mMarks = document.getString("marks");
                                    mSubject = document.getString("subject");
                                    mCity = document.getString("city");


                                    exampleList.add(new ExampleItem(R.drawable.ic_people_black_24dp,i,mName,mClass,mSchool,mBoard,mMarks,mGender,mSubject,mCity));

                                }
//                               //Fetch name from data
//                                if (document.getString("name") != null)
//                                Log.d("test", document.getId() + " => " + document.getString("name"));
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                        if (task.isComplete())
                            recyclerViewConfig();
                    }
                });


        //Query
        // Create a reference to the cities collection
//        CollectionReference studRef = db.collection("students");

// Create a query against the collection.
//        Query query = studRef.whereEqualTo("status", "1");

    }

    private void recyclerViewConfig() {
        //Config  for RV

        recyclerView = findViewById(R.id.recyclerViewTutor);
        //Performance
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ExampleAdapter(exampleList,onclickInterface2);

//        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
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
                studDetails.put("city",exampleList.get(position).getmCity());

//                Log.d("test",studDetails.toString());

                Intent gotoUserActi = new Intent(getApplicationContext(),Activity.class);
                gotoUserActi.putExtra("studDetail",studDetails);
                finish();
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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.tNav_home:
                    break;
                case R.id.tNav_editData:
                    Intent gotoStudDetails = new Intent(getApplicationContext(), TutorEdit.class);
                    startActivity(gotoStudDetails);
                    break;

                case R.id.tNav_exit:
                    finish();
            }

            return true;
        }
    };

}
