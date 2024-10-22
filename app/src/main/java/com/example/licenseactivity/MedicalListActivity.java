package com.example.licenseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicalListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyDataBase myDataBase;
    private ArrayList<MedicalRecord> medicalRecords;
    private MedicalAdapter medicalAdapter;
    FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);

        recyclerView = findViewById(R.id.recyclerView);
        myDataBase = new MyDataBase(this);

        // Preluăm toate datele din baza de date
        medicalRecords = myDataBase.getAllMedicalRecords();

        // Configurăm RecyclerView-ul
        medicalAdapter = new MedicalAdapter(this, medicalRecords);
        recyclerView.setAdapter(medicalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        // Setarea click listener-ului pentru FloatingActionButton
//        floatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Deschiderea unei noi activități (AddActivity)
//                Intent intent = new Intent(MedicalListActivity.this, AddActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}

