package com.example.overthehill;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SponsoredEventsActivity extends AppCompatActivity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_events);

        recycler = (RecyclerView) findViewById(R.id.recyclerSponsor);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Map> events = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                events.add(document.getData());
                            }
                            final EventsAdapter adapter = new EventsAdapter(events);
                            recycler.setAdapter(adapter);
                            Log.d(TAG,events.toString());
                        } else {
                            Log.w(TAG, "boobies Error getting documents.", task.getException());
                        }
                    }
                });
        /*
        final EventsAdapter adapter = new EventsAdapter(events);
        Log.d(TAG,adapter.toString() + "boobies");
        recycler.setAdapter(adapter);
         */
        recycler.setLayoutManager(new LinearLayoutManager(this));


    }

    public List<Map> getEvents(){
        List<Map> data = new ArrayList<Map>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("SponsoredEvents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map temp = document.getData();
                                data.add(temp);
                            }

                        } else {
                            Log.w(TAG, "boobies Error getting documents.", task.getException());
                        }
                    }
                });

        Log.d(TAG,data + "boobies");

        return data;
    }
}
