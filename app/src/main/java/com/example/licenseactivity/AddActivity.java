package com.example.licenseactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText name_spital, name_departament, name_doctor;
    Button add_button, delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Inițializare EditText și Button
        name_spital = findViewById(R.id.name_spital);
        name_departament = findViewById(R.id.name_department);
        name_doctor = findViewById(R.id.name_doctors);

        add_button = findViewById(R.id.add_button);
        delete_button = findViewById(R.id.delete_button);

        // Inițializare bază de date
        MyDataBase mydb = new MyDataBase(AddActivity.this);

        // Listener pentru butonul de adăugare
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.addMedical(
                        name_spital.getText().toString().trim(),
                        name_departament.getText().toString().trim(),
                        name_doctor.getText().toString().trim()
                );
            }
        });

        // Listener pentru butonul de ștergere
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Afișează dialogul pentru a selecta un ID de șters
                showDeleteDialog(mydb);
            }
        });
    }

    private void showDeleteDialog(MyDataBase mydb) {
        // Obține toate ID-urile din baza de date
        ArrayList<Integer> idList = mydb.getAllIDs();

        if (idList.isEmpty()) {
            Toast.makeText(AddActivity.this, "No entries to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertim lista de ID-uri într-un array de String-uri pentru a o afișa în AlertDialog
        String[] idArray = new String[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            idArray[i] = String.valueOf(idList.get(i));
        }

        // Construiește un AlertDialog pentru a afișa ID-urile
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select ID to Delete");

        builder.setItems(idArray, (dialog, which) -> {
            int selectedID = idList.get(which); // Obține ID-ul selectat
            mydb.deleteMedical(selectedID); // Șterge ID-ul selectat
            Toast.makeText(AddActivity.this, "Deleted entry with ID: " + selectedID, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        // Afișează dialogul
        builder.show();
    }
}