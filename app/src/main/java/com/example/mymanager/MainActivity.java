package com.example.mymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
//import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ImageView img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img4 = findViewById(R.id.icon4);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.diary:
                        Intent intent = new Intent(MainActivity.this, Daily.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });
    }
    public void navigateAddEntry(View view){
        Intent intent = new Intent(this, AddEntry.class);
        startActivity(intent);
    }


}







