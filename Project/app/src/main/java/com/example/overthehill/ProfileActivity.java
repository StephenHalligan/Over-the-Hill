package com.example.overthehill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private Button mSaveChanges;
    private EditText mName, mEmail, mLocation, mAge, mBio, mInterests;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };

        mSaveChanges = (Button) findViewById(R.id.SaveChanges);

        mName = (EditText) findViewById(R.id.Name);
        mEmail = (EditText) findViewById(R.id.Email);
        mLocation = (EditText) findViewById(R.id.Location);
        mAge = (EditText) findViewById(R.id.Age);
        mBio = (EditText) findViewById(R.id.Bio);
        mInterests = (EditText) findViewById(R.id.Interests);

        mSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = mName.getText().toString();
                final String email = mEmail.getText().toString();
                final String location = mLocation.getText().toString();
                final String age = mAge.getText().toString();
                final String bio = mBio.getText().toString();
                final String interests = mInterests.getText().toString();

                String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Name");
                currentUserDb.setValue(name);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Email");
                currentUserDb.setValue(email);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Location");
                currentUserDb.setValue(location);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Age");
                currentUserDb.setValue(age);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Bio");
                currentUserDb.setValue(bio);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Interests");
                currentUserDb.setValue(interests);

            }
        });

    }
}
