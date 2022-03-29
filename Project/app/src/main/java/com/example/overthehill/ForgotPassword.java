package com.example.overthehill;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    private Button mResetPassword;
    private EditText mEmail, mNewPassword, mReEnterNewPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmail = (EditText) findViewById(R.id.Email);
        mNewPassword = (EditText) findViewById(R.id.NewPassword);
        mReEnterNewPassword = (EditText) findViewById(R.id.ReEnterNewPassword);

        mResetPassword = (Button) findViewById(R.id.Register);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Check if user is logged in
                if (user != null) {
                    Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = mEmail.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplication(), "Enter your Email ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(Email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPassword.this, "Instructions have been sent to reset password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Please enter valid Email ID!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}