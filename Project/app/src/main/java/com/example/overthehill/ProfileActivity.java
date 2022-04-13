package com.example.overthehill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;
import androidx.annotation.NonNull;


public class ProfileActivity extends AppCompatActivity {

    private Button mSaveChanges;
    private EditText mName, mEmail, mLocation, mAge, mBio, mInterests;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0 );
                        return true;
                    case R.id.profile:
                        return true;

                }

                return false;
            }
        });


        mAuth = FirebaseAuth.getInstance();

        mSaveChanges = (Button) findViewById(R.id.saveChanges);

        mName = (EditText) findViewById(R.id.editName);
        mEmail = (EditText) findViewById(R.id.editEmail);
        mLocation = (EditText) findViewById(R.id.editLocation);
        mAge = (EditText) findViewById(R.id.editAge);
        mBio = (EditText) findViewById(R.id.editBio);
        mInterests = (EditText) findViewById(R.id.editInterests);

        //Save changes
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

                Toast.makeText(ProfileActivity.this, "Saved user data!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
