package com.example.mymanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEntry extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    EditText etTitle,etEntry;
    TextView textDateTime,textLimit;
    ImageView imageNote ;
    CheckBox cb1;
    String selectedImagePath;

    int count = 0;

    Diary diary1;
    DatabaseReference db;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_diary);
        getSupportActionBar().setTitle("  Diary");



        etTitle = findViewById(R.id.et_addTitle);
        etEntry = findViewById(R.id.et_addEntry);
        textDateTime=findViewById(R.id.textDateTime);
        imageNote= findViewById(R.id.imageNote);
        cb1= findViewById(R.id.cb_confidential);
        textLimit = findViewById(R.id.tv_limit);

        etEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text =etEntry.getText().toString();
                int symbols = text.length();
                textLimit.setText(symbols+"/300");
                if(text.length() == 300 ){
                    Toast.makeText(getApplicationContext(), "You have reached the maximum number of characters", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        Button imageSave = findViewById(R.id.imageSave);


        //imageSave.setOnClickListener(view -> saveNote());



        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked()){
                    saveConfidential();

                }else {
                    saveNote();
                }
            }
        });


        selectedImagePath = "";

        initMiscellaneous();

        diary1 = new Diary();

        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.daily);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.daily:
                        startActivity(new Intent(getApplicationContext(), Daily.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.confidential:
                        startActivity(new Intent(getApplicationContext(),Passcode.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });




    }

    private void saveNote(){
        db = FirebaseDatabase.getInstance().getReference().child("Diary");
        try {
            if (etTitle.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Title can not be empty", Toast.LENGTH_SHORT).show();
            } else if (etEntry.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Note can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                diary1.setTitle(etTitle.getText().toString());
                diary1.setEntry(etEntry.getText().toString());
                diary1.setDateTime(textDateTime.getText().toString());
                diary1.setImagePath(selectedImagePath);

                db.push().setValue(diary1);
                Toast.makeText(getApplicationContext(), "Successfully saved your diary note...", Toast.LENGTH_LONG).show();
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Number Format Exception", Toast.LENGTH_LONG).show();
        }
    }


    private void saveConfidential(){
        db = FirebaseDatabase.getInstance().getReference().child("SecretDiary");
        try {
            if (etTitle.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Note title cant be empty", Toast.LENGTH_SHORT).show();
            } else if (etEntry.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Note cant be empty", Toast.LENGTH_SHORT).show();
            } else {
                diary1.setTitle(etTitle.getText().toString());
                diary1.setEntry(etEntry.getText().toString());
                diary1.setDateTime(textDateTime.getText().toString());
                diary1.setImagePath(selectedImagePath);

                db.push().setValue(diary1);
                Toast.makeText(getApplicationContext(), "Successfully saved your Confidential note...", Toast.LENGTH_LONG).show();
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Number Format Exception", Toast.LENGTH_LONG).show();
        }
    }


    private void initMiscellaneous(){
        findViewById(R.id.btnAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            AddEntry.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                }else {
                    selectImage();
                }
            }
        });
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if (data !=null){
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null){
                    try{
                        InputStream inputStream=getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageNote.setImageBitmap(bitmap);
                        imageNote.setVisibility(View.VISIBLE);
                        selectedImagePath = getPathFromUri(selectedImageUri);
                    }catch (Exception exception){
                        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private  String getPathFromUri(Uri contentUri) {
        String filepath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filepath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filepath = cursor.getString(index);
            cursor.close();
        }
        return filepath;
    }


    public void Cancel(View view){
        startActivity(new Intent(getApplicationContext(), Daily.class));
        overridePendingTransition(0, 0);

    }
}
