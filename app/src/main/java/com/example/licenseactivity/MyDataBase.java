package com.example.licenseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MyDataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASENAME = "HotSpitalDataBase.db";
    private static final int DATABASEVERSION = 3;
    private static final String TABLENAME = "HotSpitalReserved";
    private static final String COLUMNID = "ID";
    private static final String COLUMNNAME = "NameHotSpital";
    private static final String COLUMNDEPARTMENT = "Department";
    private static final String COLUMNDOCTORS = "NameDoctors";
    public MyDataBase(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLENAME + " (" +
                COLUMNID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNNAME + " TEXT, " +
                COLUMNDEPARTMENT + " TEXT, " +
                COLUMNDOCTORS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    void addMedical(String spital, String departament, String doctor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNNAME, spital);
        cv.put(COLUMNDEPARTMENT, departament);
        cv.put(COLUMNDOCTORS, doctor);
        long result = db.insert(TABLENAME, null, cv);
        if(result == -1)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else{
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteMedical(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLENAME, COLUMNID + "=?", new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    // Adaugă această metodă în clasa MyDataBase
    public ArrayList<Integer> getAllIDs() {
        ArrayList<Integer> idList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMNID + " FROM " + TABLENAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                idList.add(id);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return idList;
    }

    // Metodă pentru a obține toate spitalele
    public ArrayList<String> getSpitalList() {
        ArrayList<String> hospitals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMNNAME + " FROM " + TABLENAME, null);

        if (cursor.moveToFirst()) {
            do {
                String hospital = cursor.getString(0);
                hospitals.add(hospital);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return hospitals;
    }

    // Metodă pentru a obține toate departamentele
    public ArrayList<String> getDepartamentList() {
        ArrayList<String> departments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMNDEPARTMENT + " FROM " + TABLENAME, null);

        if (cursor.moveToFirst()) {
            do {
                String department = cursor.getString(0);
                departments.add(department);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return departments;
    }

    // Metodă pentru a obține toți doctorii pe baza unui departament selectat
    public ArrayList<String> getDoctorList() {
        ArrayList<String> doctors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT " + COLUMNDOCTORS + " FROM " + TABLENAME  , null);

        if (cursor.moveToFirst()) {
            do {
                String doctor = cursor.getString(0);
                doctors.add(doctor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return doctors;
    }

    public ArrayList<MedicalRecord> getAllMedicalRecords() {
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLENAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMNID));
                String spital = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNNAME));
                String departament = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNDEPARTMENT));
                String doctor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNDOCTORS));

                MedicalRecord record = new MedicalRecord(id, spital, departament, doctor);
                medicalRecords.add(record);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medicalRecords;
    }

    //metoda asta o folosesc pentru a afisa in main doctorii, departamentele si spitalele
    //,dar momentan nu functioneaza legatura dintre CustomAdapter si Main
    //pentru ca atunci cand bag credentialele admin-ului ma intoarce in CustomAdapter
    // Citește toate datele din tabel
//    Cursor readData() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        if (db != null) {
//            Cursor cursor = db.rawQuery("SELECT " + COLUMNID + " FROM " + TABLENAME, null);
//            if (cursor != null && cursor.getCount() > 0) {
//                Log.d("MyDataBase", "Data found in table");
//                return cursor;  // Returnează cursorul valid
//            } else {
//                Log.d("MyDataBase", "No data found in table");
//                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Log.e("MyDataBase", "Database is null");
//        }
//        return null;  // Returnează null dacă nu s-au găsit date
//    }
}
