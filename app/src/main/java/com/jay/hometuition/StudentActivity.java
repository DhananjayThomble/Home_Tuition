package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Fragment selectedFragment= new StudentHomeFragment();
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Home Tuition");

        bottomNav = findViewById(R.id.student_bottom_nav);


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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    break;
                case R.id.nav_tutor:
                    Intent gotoStudDetails = new Intent(StudentActivity.this,StudDetail.class);
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
