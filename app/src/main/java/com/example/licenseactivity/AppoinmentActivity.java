package com.example.licenseactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class AppoinmentActivity extends AppCompatActivity {

    EditText editTextPatientName, editTextDate;
    Spinner spinnerSpital, spinnerDepartament, spinnerDoctor;
    Button buttonSubmit;
    FloatingActionButton floatingButton;
    MyDataBase myDataBase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);

        // Inițializarea widget-urilor
        editTextPatientName = findViewById(R.id.editTextPatientName);
        editTextDate = findViewById(R.id.editTextDate);
        spinnerSpital = findViewById(R.id.spinner_spital);
        spinnerDepartament = findViewById(R.id.spinner_departament);
        spinnerDoctor = findViewById(R.id.spinner_doctor);
        buttonSubmit = findViewById(R.id.button_submit);
        floatingButton = findViewById(R.id.floating_button);

        // Inițializarea bazei de date
        myDataBase = new MyDataBase(this);

        // Popularea spinner-elor cu date din baza de date sau liste predefinite
        populateSpinners();

        // Setarea click listener-ului pentru butonul de submit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointment();
            }
        });

        // Setarea click listener-ului pentru FloatingActionButton
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschiderea unei noi activități (AddActivity)
                Intent intent = new Intent(AppoinmentActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void populateSpinners() {
        // Obține lista de spitale din baza de date
        ArrayList<String> spitalList = myDataBase.getSpitalList();
        ArrayAdapter<String> spitalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spitalList);
        spitalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpital.setAdapter(spitalAdapter);

        // Obține lista de departamente din baza de date
        ArrayList<String> departamentList = myDataBase.getDepartamentList();
        ArrayAdapter<String> departamentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departamentList);
        departamentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartament.setAdapter(departamentAdapter);

        // Obține lista de doctori din baza de date
        ArrayList<String> doctorList = myDataBase.getDoctorList();
        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(doctorAdapter);
    }

    public void addAppointment() {
        // Obținerea valorilor introduse de utilizator
        String patientName = editTextPatientName.getText().toString().trim();
        String selectedSpital = spinnerSpital.getSelectedItem().toString();
        String selectedDepartament = spinnerDepartament.getSelectedItem().toString();
        String selectedDoctor = spinnerDoctor.getSelectedItem().toString();
        String dataProgramare = editTextDate.getText().toString().trim();

        // Validarea datelor
        if (patientName.isEmpty()) {
            Toast.makeText(this, "Te rugăm să completezi toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dataProgramare.isEmpty()) {
            Toast.makeText(this, "Te rugăm să completezi toate câmpurile", Toast.LENGTH_SHORT).show();
            return;
        }

        // Adăugarea programării în baza de date
        myDataBase.addMedical(selectedSpital, selectedDepartament, selectedDoctor);

        // Afișarea unui mesaj de confirmare
        Toast.makeText(this, "Programarea a fost adăugată", Toast.LENGTH_SHORT).show();

        // Resetarea câmpurilor după adăugare
        editTextPatientName.setText("");
        spinnerSpital.setSelection(0);
        spinnerDepartament.setSelection(0);
        spinnerDoctor.setSelection(0);
    }
}