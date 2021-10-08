package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Daily extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE = 1;

    RecyclerView dailyRecyclerView;
    ArrayList<Diary> diaries;
    DiaryAdapter diaryAdapter;
    Diary diary;
    TextView count;

    DatabaseReference db;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_diary);
        getSupportActionBar().setTitle("  Diary");



        count = findViewById(R.id.tv_count);
        dailyRecyclerView = findViewById(R.id.rt_daily);
        db = FirebaseDatabase.getInstance().getReference("Diary");
        dailyRecyclerView.setHasFixedSize(true);

        dailyRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        diaries = new ArrayList<>();
        diaryAdapter = new DiaryAdapter(this, diaries);
        dailyRecyclerView.setAdapter(diaryAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Diary diary = dataSnapshot.getValue(Diary.class);
                    diaries.add(diary);
            }
                diaryAdapter.notifyDataSetChanged();
                count.setText("Total dairy notes = "+""+ diaries.size());

        }


        @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.daily);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.daily:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.confidential:
                        startActivity(new Intent(getApplicationContext(),Passcode.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
    }

    public void Write(View view){
        startActivity(new Intent(getApplicationContext(), AddEntry.class));
        overridePendingTransition(0, 0);

    }

}