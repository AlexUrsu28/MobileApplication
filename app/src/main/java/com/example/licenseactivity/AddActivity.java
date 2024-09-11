package com.example.licenseactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText name_spital, name_departament, name_doctor;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name_spital = findViewById(R.id.name_spital);
        name_departament = findViewById(R.id.name_department);
        name_doctor = findViewById(R.id.name_doctors);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase mydb = new MyDataBase(AddActivity.this);
                mydb.addMedical(name_spital.getText().toString().trim(),
                        name_departament.getText().toString().trim(),
                        name_doctor.getText().toString().trim());
            }
        });
        }
}