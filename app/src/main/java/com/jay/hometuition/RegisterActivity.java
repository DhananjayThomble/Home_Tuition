package com.jay.hometuition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPass, mMobile, mPass2;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private CircularProgressButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        //Assign Button
        mName = findViewById(R.id.editTextName);
        mEmail = findViewById(R.id.editTextEmail);
        mMobile = findViewById(R.id.editTextMobile);
        mPass = findViewById(R.id.editTextPass);
        mPass2 = findViewById(R.id.editTextPass2);
        btnSubmit = findViewById(R.id.cirRegisterButton);
        mAuth = FirebaseAuth.getInstance();


    }

    public void btnSubmitClick(View v) {

        if ((TextUtils.isEmpty(mEmail.getText().toString())) && (TextUtils.isEmpty(mPass.getText().toString())) &&
                (TextUtils.isEmpty(mName.getText().toString())) &&
                (TextUtils.isEmpty(mMobile.getText().toString())) &&
                (TextUtils.isEmpty(mPass2.getText().toString()))) {

            Toast.makeText(this, "Fill up the input", Toast.LENGTH_SHORT).show();

        } else{
            btnSubmit.startAnimation();
            //Sign Up
            signUp(mEmail.getText().toString(), mPass.getText().toString());
        }

    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    // Validate User Input
    private boolean isValid(String mEmail, String mPass1, String mPass2) {
        if (mEmail != null && mPass1 != null && mPass2 != null) {
            return mPass1.equals(mPass2);
        }
        return false;
    }

    // Create New User
    private void signUp(String mEmail, String mPass) {
        mAuth.createUserWithEmailAndPassword(mEmail, mPass)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        currentUser = mAuth.getCurrentUser();
//                        updateUI(currentUser);
                        sendEmailVerification();
                        storeDetails();
                        btnSubmit.revertAnimation();
                    }
                })

                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        Log.w("test", "Auth Failed", e);
//                        updateUI(null);
                    }
                });
    }

    private void storeDetails() {
        Map<String, Object> user = new HashMap<>();
        String id = mAuth.getUid();
        user.put("id", id);
        user.put("name", mName.getText().toString());
        user.put("mobile", mMobile.getText().toString());

        // Access a Cloud Firestore instance from your Activity
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("test", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(RegisterActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test", "Error adding document", e);
                    }
                });

    }

    private void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]


                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this,
                                        "Verification email sent to " + currentUser.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("test", "sendEmailVerification", task.getException());
                                Toast.makeText(RegisterActivity.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END send_email_verification]
    }

    public void onLogicClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

    }


}
