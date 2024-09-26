package com.example.licenseactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle_view;
    FloatingActionButton floating_button;
    MyDataBase mydb;
    ArrayList<Integer> id_med;
    ArrayList<String> spital, departament, doctor;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "MainActivity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle_view = findViewById(R.id.recycle_view);
        floating_button = findViewById(R.id.floating_button);
        floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        mydb = new MyDataBase(MainActivity.this);
        id_med = new ArrayList<>();
        spital = new ArrayList<>();
        departament = new ArrayList<>();
        doctor = new ArrayList<>();

        displayData();

        customAdapter = new CustomAdapter(MainActivity.this, id_med, spital, departament, doctor);
        recycle_view.setAdapter(customAdapter);
        recycle_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //customAdapter.notifyDataSetChanged();  // Adaugă acest rând dacă nu este deja
    }

    void displayData(){
        Cursor cursor = mydb.readData();
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                //Mereu cand comentez linia asta ma duce in Main,
                // iar cand o las valida din login ma intoarce innapoi in SignUp
                id_med.add(cursor.getInt(0));
                spital.add(cursor.getString(1));
                departament.add(cursor.getString(2));
                doctor.add(cursor.getString(3));
            }
        }
        cursor.close();
    }
}

