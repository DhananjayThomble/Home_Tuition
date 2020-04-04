package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.loginButton);

        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.login_activity);
    }

    public void onLoginSubmit(View view){
        EditText mEmail,mPass;
        mEmail = findViewById(R.id.txtEmail);
        mPass = findViewById(R.id.txtPass);
        if ( mEmail != null && mPass != null ){
            signIN(mEmail.getText().toString(),mPass.getText().toString());
        }
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }

    //Sign In
    private void signIN(String mEmail, String mPass){
        mAuth.signInWithEmailAndPassword(mEmail,mPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                            currentUser = mAuth.getCurrentUser();
//                            updateUI(currentUser);
//                            students.setmFirebaseUser(currentUser.getUid());
                            new CountDownTimer(3000,1000){

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    Toast.makeText(LoginActivity.this,"You will be Redirected to Dashboard",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFinish() {
//                                    //TODO goto which class
//                                    Intent intent = new Intent(ActivityLogin.this,Rlearners.class);
//                                    finish();
//                                    startActivity(intent);
                                }
                            }.start();
                        }
                        else
                            Toast.makeText(LoginActivity.this,"Auth Failed",Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test",e);
//                        updateUI(null);
                    }
                });
    }
}
