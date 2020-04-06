package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private CircularProgressButton btnSubmit;
    private Students students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btnSubmit = findViewById(R.id.cirRegisterButton2);

        students = new Students();

        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.login_activity);



    }

    public void onLoginSubmit(View view) {
//////Un comment this :-
        EditText mEmail, mPass;
        mEmail = findViewById(R.id.txtEmail);
        mPass = findViewById(R.id.txtPass);
        if (TextUtils.isEmpty(mEmail.getText().toString()) && TextUtils.isEmpty(mPass.getText().toString()))
//            TODO make Graphical Toast
            Toast.makeText(LoginActivity.this, "Fill the input", Toast.LENGTH_SHORT).show();
        else
            signIN(mEmail.getText().toString(), mPass.getText().toString());
//
//        Intent intent = new Intent(LoginActivity.this, ActivityDashboard.class);
//        finish();
//        startActivity(intent);

    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }

    //Sign In
    private void signIN(String mEmail, String mPass) {
        mAuth.signInWithEmailAndPassword(mEmail, mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            currentUser = mAuth.getCurrentUser();
//                            updateUI(currentUser);
                            students.setmFirebaseUser(currentUser.getUid());
                            Toast.makeText(LoginActivity.this, "You will be Redirected to Dashboard", Toast.LENGTH_SHORT).show();

                            new CountDownTimer(2000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
//                                    //
                                    Intent intent = new Intent(LoginActivity.this, ActivityDashboard.class);
                                    finish();
                                    startActivity(intent);
                                }
                            }.start();
                        } else
                            Toast.makeText(LoginActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", e);
//                        updateUI(null);
                    }
                });
    }
}
